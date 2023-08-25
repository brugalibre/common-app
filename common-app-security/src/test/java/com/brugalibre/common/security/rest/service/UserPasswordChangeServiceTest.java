package com.brugalibre.common.security.rest.service;

import com.brugalibre.common.security.auth.config.TestCommonAppSecurityConfig;
import com.brugalibre.common.security.auth.passwordchange.UserPasswordChangedEvent;
import com.brugalibre.common.security.auth.passwordchange.UserPasswordChangedObserver;
import com.brugalibre.common.security.rest.api.AuthController;
import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.rest.model.passwordchange.UserPasswordChangeRequest;
import com.brugalibre.common.security.rest.service.passwordchange.UserUserPasswordChangeService;
import com.brugalibre.common.security.user.repository.UserDetailsServiceImpl;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = TestCommonAppSecurityConfig.class)
class UserPasswordChangeServiceTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUserPasswordChangeService userPasswordChangeService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void changeUserPassword() {
        // Given
        String newPassword = "newPassword";
        String expectedPersistedNewPassword = encoder.encode(newPassword);
        String username = "hans";
        String userPhoneNr = "0712223344";
        String password = "1234";
        RegisterRequest registerRequest = new RegisterRequest(username, password, userPhoneNr, List.of("USER"));
        authController.registerUser(registerRequest);
        UserPasswordChangedObserver userPasswordChangedObserver = mock(UserPasswordChangedObserver.class);
        userPasswordChangeService.addUserPasswordChangedObserver(userPasswordChangedObserver);

        // When
        User user = userRepository.getAll().get(0);
        UserPasswordChangeRequest userPasswordChangeRequest = new UserPasswordChangeRequest(user.id(), newPassword);
        authController.changePassword(userPasswordChangeRequest);

        // Then
        List<User> users = userRepository.getAll();
        assertThat(users.size(), is(1));
        assertThat(users.get(0).username(), is(username));
        assertThat(users.get(0).getMobilePhone().getPhoneNr(), is(userPhoneNr));
        assertThat(users.get(0).password(), is(expectedPersistedNewPassword));
        org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);
        assertThat(securityUser.getAuthorities().size(), is(1));
        assertThat(new ArrayList<>(securityUser.getAuthorities()).get(0).getAuthority(), is(Role.USER.name()));
        verify(userPasswordChangedObserver).passwordChanged(Mockito.eq(UserPasswordChangedEvent.of(user.id(), newPassword)));
    }

    @Test
    void changeUserPasswordExceptionFromObserver() {
        // Given
        String newPassword = "newPassword";
        String username = "hans";
        String userPhoneNr = "0712223344";
        String password = "1234";
        UserPasswordChangedObserver userPasswordChangedObserver = obs -> {
            throw new IllegalStateException();
        };
        userPasswordChangeService.addUserPasswordChangedObserver(userPasswordChangedObserver);
        RegisterRequest registerRequest = new RegisterRequest(username, password, userPhoneNr, List.of("USER"));
        authController.registerUser(registerRequest);

        // When
        User user = userRepository.getAll().get(0);
        UserPasswordChangeRequest userPasswordChangeRequest = new UserPasswordChangeRequest(user.id(), newPassword);
        try {
            authController.changePassword(userPasswordChangeRequest);
        } catch (IllegalStateException e) {
            // ignore
        }

        // Then
        List<User> users = userRepository.getAll();
        assertThat(users.size(), is(1));
        assertThat(users.get(0).password(), is(password));
        userPasswordChangeService.removeUserPasswordChangedObserver(userPasswordChangedObserver);
    }
}
package com.brugalibre.domain.user.service;

import com.brugalibre.common.persistence.transaction.TransactionHelper;
import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.model.change.ChangeEventType;
import com.brugalibre.domain.user.model.change.UserChangedEvent;
import com.brugalibre.domain.user.model.change.UserChangedObserver;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionHelper transactionHelper;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void changePassword() throws Exception {
        // Given
        String oldPassword = "password";
        String newPassword = "newPassword";
        User user = User.of("Peter", oldPassword, MobilePhone.of("0711234567"));

        User persistentUser = userService.persist(user);

        // When
        User changedUser = transactionHelper.runTransactional(() -> userService.changePassword(newPassword, persistentUser.id()));

        // Then
        assertThat(changedUser.password()).isEqualTo(newPassword);
    }

    @Test
    void createUsersFromYamlFile() {
        // Given
        String ymlFile = "config/test-user-yaml-input.yaml";

        // When
        List<User> persistentUsers = userService.createFromYaml(ymlFile);

        // Then
        assertThat(persistentUsers.size()).isEqualTo(2);
        assertThat(persistentUsers.get(0).username()).isEqualTo("test-user");
        assertThat(persistentUsers.get(0).getMobilePhone().getPhoneNr()).isEqualTo("0711234567");
        assertThat(persistentUsers.get(0).roles()).isEqualTo(List.of(Role.USER, Role.ADMIN));
        assertThat(persistentUsers.get(1).username()).isEqualTo("test-user2");
        assertThat(persistentUsers.get(1).getMobilePhone().getPhoneNr()).isEqualTo("0041791234567");
        assertThat(persistentUsers.get(1).roles()).isEqualTo(List.of(Role.USER));
    }

    @Test
    void changeUsername() throws Exception {
        // Given
        String oldUsername = "peter";
        String newUsername = "hans-peter";
        String password = "234";
        User user = User.of(oldUsername, password, MobilePhone.of("0711234567"));
        UserChangedObserver userChangedObserver = mock(UserChangedObserver.class);
        userService.addUserRegisteredObserver(userChangedObserver);

        User persistentUser = userService.persist(user);

        // When
        User changedUser = transactionHelper.runTransactional(() -> userService.changeUsername(newUsername, persistentUser.id()));

        // Then
        assertThat(changedUser.username()).isEqualTo(newUsername);
        verify(userChangedObserver).userChanged(eq(UserChangedEvent.of(persistentUser.id(), newUsername, ChangeEventType.USER_NAME)));
        userService.removeUserRegisteredObserver(userChangedObserver);
    }

    @Test
    void changeUsernameWithVetoFromObserver() throws Exception {
        // Given
        String oldUsername = "peter";
        String newUsername = "hans-peter";
        String password = "234";
        User user = User.of(oldUsername, password, MobilePhone.of("0711234567"));

        User persistentUser = userService.persist(user);
        VetoUserChangedObserver userChangedObserver = new VetoUserChangedObserver();
        userService.addUserRegisteredObserver(userChangedObserver);

        // When
        try {
            transactionHelper.runTransactional(() -> userService.changeUsername(newUsername, persistentUser.id()));
        } catch (IllegalStateException e) {
            // no-op
        }
        user = userService.getById(persistentUser.id());

        // Then
        assertThat(user.username()).isEqualTo(oldUsername);
        userService.removeUserRegisteredObserver(userChangedObserver);
    }
}
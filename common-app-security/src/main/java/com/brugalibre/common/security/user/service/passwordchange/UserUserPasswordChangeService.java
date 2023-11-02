package com.brugalibre.common.security.user.service.passwordchange;

import com.brugalibre.common.security.auth.passwordchange.UserPasswordChangedEvent;
import com.brugalibre.common.security.auth.passwordchange.UserPasswordChangedNotifier;
import com.brugalibre.common.security.auth.passwordchange.UserPasswordChangedObserver;
import com.brugalibre.common.security.i18n.TextResources;
import com.brugalibre.common.security.user.model.passwordchange.UserPasswordChangeRequest;
import com.brugalibre.common.security.user.model.passwordchange.UserPasswordChangeResponse;
import com.brugalibre.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class UserUserPasswordChangeService implements UserPasswordChangedNotifier {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final List<UserPasswordChangedObserver> userPasswordChangedObservers;

    @Autowired
    public UserUserPasswordChangeService(UserService userService, PasswordEncoder encoder) {
        this.userPasswordChangedObservers = new ArrayList<>();
        this.userService = userService;
        this.encoder = encoder;
    }

    @Transactional
    public UserPasswordChangeResponse changeUserPassword(UserPasswordChangeRequest userPasswordChangeRequest) {
        String encodedPwd = encoder.encode(userPasswordChangeRequest.newPassword());
        userService.changePassword(encodedPwd, userPasswordChangeRequest.userId());
        notifyPasswordChanged(userPasswordChangeRequest);
        return new UserPasswordChangeResponse(TextResources.PASSWORD_CHANGE_SUCCESSFUL);
    }

    private void notifyPasswordChanged(UserPasswordChangeRequest userPasswordChangeRequest) {
        UserPasswordChangedEvent userPasswordChangedEvent = UserPasswordChangedEvent.of(userPasswordChangeRequest.userId(), userPasswordChangeRequest.newPassword());
        userPasswordChangedObservers.forEach(userPasswordChangedObserver -> userPasswordChangedObserver.passwordChanged(userPasswordChangedEvent));
    }

    @Override
    public void addUserPasswordChangedObserver(UserPasswordChangedObserver userPasswordChangedObserver) {
        userPasswordChangedObservers.add(requireNonNull(userPasswordChangedObserver));
    }

    @Override
    public void removeUserPasswordChangedObserver(UserPasswordChangedObserver userPasswordChangedObserver) {
        userPasswordChangedObservers.remove(userPasswordChangedObserver);
    }
}

package com.brugalibre.common.security.auth.passwordchange;

import com.brugalibre.domain.user.model.User;

/**
 * The {@link UserPasswordChangedObserver} can be implemented by anyone who likes to be informed about any changes of a {@link User}
 */
public interface UserPasswordChangedObserver {

    /**
     * Notifies that a {@link User} has been changed
     *
     * @param userPasswordChangedEvent the {@link UserPasswordChangedEvent}
     */
    void passwordChanged(UserPasswordChangedEvent userPasswordChangedEvent);
}

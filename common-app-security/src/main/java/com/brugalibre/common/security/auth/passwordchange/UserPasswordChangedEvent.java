package com.brugalibre.common.security.auth.passwordchange;

import com.brugalibre.domain.user.model.User;

/**
 * The {@link UserPasswordChangedEvent} is an event which is fired everytime a new {@link User} has changed
 *
 * @param userId      the technical id of the user
 * @param newPassword the new password
 */
public record UserPasswordChangedEvent(String userId, Object newPassword) {
    /**
     * Creates a new {@link UserPasswordChangedEvent} for the given registered {@link User}
     *
     * @param userId      the technical id of the user
     * @param newPassword the new password
     * @return a {@link UserPasswordChangedEvent}
     */
    public static UserPasswordChangedEvent of(String userId, String newPassword) {
        return new UserPasswordChangedEvent(userId, newPassword);
    }
}

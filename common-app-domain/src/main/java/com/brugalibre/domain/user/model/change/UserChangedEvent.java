package com.brugalibre.domain.user.model.change;

import com.brugalibre.domain.user.model.User;

/**
 * The {@link UserChangedEvent} is an event which is fired everytime a new {@link User} has changed
 *
 * @param userId          the technical id of the user
 * @param newValue        the new value
 * @param changeEventType defines the changed attribute
 */
public record UserChangedEvent(String userId, Object newValue, ChangeEventType changeEventType) {
    /**
     * Creates a new {@link UserChangedEvent} for the given registered {@link User}
     *
     * @param userId          the technical id of the user
     * @param newValue        the new value
     * @param changeEventType defines the changed attribute
     * @return a {@link UserChangedEvent}
     */
    public static UserChangedEvent of(String userId, Object newValue, ChangeEventType changeEventType) {
        return new UserChangedEvent(userId, newValue, changeEventType);
    }
}

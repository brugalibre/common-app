package com.brugalibre.domain.user.model.change;

import com.brugalibre.domain.user.model.User;

/**
 * The {@link UserChangedObserver} can be implemented by anyone who likes to be informed about any changes of a {@link User}
 */
public interface UserChangedObserver {

    /**
     * Notifies that a {@link User} has been changed
     *
     * @param userChangedEvent the {@link UserChangedEvent}
     */
    void userChanged(UserChangedEvent userChangedEvent);
}

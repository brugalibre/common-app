package com.brugalibre.domain.user.service;

import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.model.change.ChangeEventType;
import com.brugalibre.domain.user.model.change.UserChangedEvent;
import com.brugalibre.domain.user.model.change.UserChangedNotifier;
import com.brugalibre.domain.user.model.change.UserChangedObserver;
import com.brugalibre.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class UserService implements UserChangedNotifier {

    private final UserRepository userRepository;
    private final List<UserChangedObserver> userChangedObservers;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userChangedObservers = new ArrayList<>();
    }

    /**
     * Persists the given {@link User} and returns the updated instance
     *
     * @param user the user to persist
     * @return an updated instance of the user
     */
    public User persist(User user) {
        return userRepository.save(user);
    }

    /**
     * Changes the {@link User}s password
     *
     * @param newPassword the new password value to set
     * @param userId      the id of the User whose password has to be changed
     * @return a changed user instance
     */
    public User changePassword(String newPassword, String userId) {
        userRepository.changePassword(newPassword, userId);
        return userRepository.getById(userId);
    }

    /**
     * Changes the {@link User}s password
     *
     * @param newUsername the new username value to set
     * @param userId      the id of the User whose username has to be changed
     * @return a changed user instance
     */
    public User changeUsername(String newUsername, String userId) {
        userRepository.changeUsername(newUsername, userId);
        notifyUserChanged(newUsername, userId);
        return userRepository.getById(userId);
    }

    private void notifyUserChanged(String newUsername, String userId) {
        UserChangedEvent userChangedEvent = UserChangedEvent.of(userId, newUsername, ChangeEventType.USER_NAME);
        userChangedObservers.forEach(userRegisteredObserver -> userRegisteredObserver.userChanged(userChangedEvent));
    }

    @Override
    public void addUserRegisteredObserver(UserChangedObserver userChangedObserver) {
        this.userChangedObservers.add(requireNonNull(userChangedObserver));
    }

    @Override
    public void removeUserRegisteredObserver(UserChangedObserver userChangedObserver) {
        this.userChangedObservers.remove(userChangedObserver);
    }

    public User getById(String userId) {
        return userRepository.getById(userId);
    }
}

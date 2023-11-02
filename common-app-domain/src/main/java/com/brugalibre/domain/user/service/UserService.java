package com.brugalibre.domain.user.service;

import com.brugalibre.domain.user.mapper.UserEntityMapper;
import com.brugalibre.domain.user.mapper.UserEntityMapperImpl;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.model.change.ChangeEventType;
import com.brugalibre.domain.user.model.change.UserChangedEvent;
import com.brugalibre.domain.user.model.change.UserChangedNotifier;
import com.brugalibre.domain.user.model.change.UserChangedObserver;
import com.brugalibre.domain.user.model.yaml.UserYamlInput;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.util.file.yml.YamlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class UserService implements UserChangedNotifier {

    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;
    private final YamlService yamlService;
    private final List<UserChangedObserver> userChangedObservers;

    public UserService(UserRepository userRepository) {
        this.userEntityMapper = new UserEntityMapperImpl();
        this.userRepository = userRepository;
        this.yamlService = new YamlService();
        this.userChangedObservers = new ArrayList<>();
    }

    /**
     * Deletes all {@link User}s
     */
    public void deleteAll() {
        userRepository.deleteAll();
    }

    /**
     * Creates one or more {@link User} which are defined in the given yaml-file.
     * <b>Note:</b> This yaml-file must contain a file structured according to the {@link UserYamlInput} class
     *
     * @param yamlFile the yaml-file containing a {@link UserYamlInput}
     * @return a list of persisted {@link User}s
     */
    public List<User> createFromYaml(String yamlFile) {
        UserYamlInput userYamlInputs = yamlService.readYaml(yamlFile, UserYamlInput.class);
        return userEntityMapper.map2Users(userYamlInputs.getUserYamlEntries())
                .stream()
                .map(this::persist)
                .toList();
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

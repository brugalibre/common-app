package com.brugalibre.domain.user.service;

import com.brugalibre.domain.user.model.change.UserChangedEvent;
import com.brugalibre.domain.user.model.change.UserChangedObserver;

public class VetoUserChangedObserver implements UserChangedObserver {
    @Override
    public void userChanged(UserChangedEvent userChangedEvent) {
        throw new IllegalStateException();
    }
}

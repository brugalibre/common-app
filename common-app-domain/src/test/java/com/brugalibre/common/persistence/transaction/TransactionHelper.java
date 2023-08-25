package com.brugalibre.common.persistence.transaction;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * The {@link TransactionHelper} is a simple bean which executes a {@link Callable}
 * within a transaction. This allows to persist certain db changes with adapting the actual implementing
 * (e.g. wrapping the actual implementation into a transaction)
 */
@Service
public class TransactionHelper {
    @Transactional
    public <T> T runTransactional(Callable<T> runnable) throws Exception {
        return runnable.call();
    }
}
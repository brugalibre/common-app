package com.brugalibre.util.lazy;

import com.brugalibre.util.hashkey.HashKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * The {@link TtlLazyValueBiFunction} is a {@link BiFunction} and provides therefore a value for two input values.
 * Additionally, it decorates the {@link BiFunction} in order to provide some kind of caching functionality and avoid
 * unnecessary calls.
 * Meaning: As long as the time-to-live for a previously evaluated value (for a given pair of input values) is still valid,
 * this very value is returned and no {@link BiFunction#apply(Object, Object)} is called.
 * <p>
 * Valid in that context means, that the ttl is greater than
 * {@link System#currentTimeMillis()} - <code>timeStampFromLastCall</code>
 */
public class TtlLazyValueBiFunction<T, U, R> implements BiFunction<T, U, R> {
   private final long timeToLife;
   private final BiFunction<T, U, R> biFunction;

   private final Map<HashKey, Long> hashKey2LastTimestampMap;
   private final Map<HashKey, R> hashKey2ValueMap;

   /**
    * Creates a new {@link TtlLazyValueBiFunction}
    *
    * @param timeToLife the ttl. Set to a number smaller or equal than 0 if the given supplier should always be called
    * @param biFunction the actual {@link BiFunction} which provides the value
    */
   public TtlLazyValueBiFunction(long timeToLife, BiFunction<T, U, R> biFunction) {
      this.timeToLife = timeToLife;
      this.biFunction = biFunction;
      this.hashKey2ValueMap = new HashMap<>();
      this.hashKey2LastTimestampMap = new HashMap<>();
   }

   @Override
   public R apply(T t, U u) {
      return applyInternal(System.currentTimeMillis(), t, u, false);
   }

   /**
    * Forces this {@link TtlLazyValueBiFunction} to call it's underlying {@link BiFunction} and therefore
    * renew its value and reset its time-to-life
    *
    * @param t the first function argument
    * @param u the second function argument
    * @return the result from the underlying {@link BiFunction}
    */
   public R applyForced(T t, U u) {
      return applyInternal(System.currentTimeMillis(), t, u, true);
   }

   private R applyInternal(long now, T t, U u, boolean forceApply) {
      HashKey hashKey = HashKey.of(t, u);
      if (!forceApply && isValueStillValid(now, hashKey)) {
         return hashKey2ValueMap.get(hashKey);
      }
      R value = biFunction.apply(t, u);
      this.hashKey2ValueMap.put(hashKey, value);
      this.hashKey2LastTimestampMap.put(hashKey, System.currentTimeMillis());
      return value;
   }

   private boolean isValueStillValid(long now, HashKey hashKey) {
      long timestamp = hashKey2LastTimestampMap.getOrDefault(hashKey, 0L);
      return timestamp != 0
              && (now - timestamp) < timeToLife;
   }
}

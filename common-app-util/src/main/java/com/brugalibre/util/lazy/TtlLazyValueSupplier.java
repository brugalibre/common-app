package com.brugalibre.util.lazy;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * The {@link TtlLazyValueSupplier} is a {@link TtlLazyValueBiFunction} but with a simple {@link Supplier} as value
 * provider instead a {@link BiFunction}.
 * <b>Note:</b>The arguments passed into the {@link BiFunction#apply(Object, Object)} method of this {@link TtlLazyValueSupplier}
 * are ignored
 */
public class TtlLazyValueSupplier<R> extends TtlLazyValueBiFunction<Object, Object, R> implements Supplier<R> {

   /**
    * Creates a new {@link TtlLazyValueBiFunction}
    *
    * @param timeToLife the ttl. Set to a number smaller or equal than 0 if the given supplier should always be called
    * @param supplier   the actual {@link Supplier} which provides the value
    */
   public TtlLazyValueSupplier(long timeToLife, Supplier<R> supplier) {
      super(timeToLife, (t, u) -> supplier.get());
   }

   /**
    * Convenient method since there are no arguments
    * Calls {@link BiFunction#apply(Object, Object)} internal
    *
    * @see TtlLazyValueBiFunction#apply(Object, Object)
    */
   @Override
   public R get() {
      return super.apply(null, null);
   }

   /**
    * Forces this {@link TtlLazyValueSupplier} to call it's underlying {@link Supplier} and therefore
    * renew its value and reset its time-to-life
    *
    * @return the result from the underlying {@link Supplier}
    */
   public R getForced() {
      return super.applyForced(null, null);
   }
}
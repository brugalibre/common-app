package com.brugalibre.util.lazy;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class TtlLazyValueBiFunctionTest {

   @Test
   void callWithSupplierAlwaysCallGet_ZeroTtl() {
      // Given
      Supplier<String> valueSupplier = () -> String.valueOf(Math.random());
      TtlLazyValueSupplier<String> stringTtlLazyValueBiFunction = new TtlLazyValueSupplier<>(0, valueSupplier);

      // When
      String firstValue = stringTtlLazyValueBiFunction.get();
      String secondValue = stringTtlLazyValueBiFunction.get();

      // When
      assertThat(firstValue).isNotEqualTo(secondValue);
   }

   @Test
   void callAlwaysCallGet_ZeroTtl() {
      // Given
      BiFunction<String, String, String> biFunction = new TestValueBiFunction();
      TtlLazyValueBiFunction<String, String, String> stringTtlLazyValueBiFunction = new TtlLazyValueBiFunction<>(0, biFunction);

      // When
      String firstValue = stringTtlLazyValueBiFunction.apply("t1", "t2");
      String secondValue = stringTtlLazyValueBiFunction.apply("t1", "t2");

      // When
      assertThat(firstValue).isNotEqualTo(secondValue);
   }

   @Test
   void callAlwaysCallGet_NonZeroTtl_AndWait() {
      // Given
      BiFunction<String, String, String> biFunction = new TestValueBiFunction();
      int ttl = 1_000;
      int tolerance = 200;
      TtlLazyValueBiFunction<String, String, String> stringTtlLazyValueBiFunction = new TtlLazyValueBiFunction<>(ttl, biFunction);

      // When
      String firstValue = stringTtlLazyValueBiFunction.apply("t1", "t2");
      await().atMost(new Duration(ttl + tolerance, TimeUnit.MILLISECONDS)).until(() -> {
         String secondValue = stringTtlLazyValueBiFunction.apply("t1", "t2");
         return !secondValue.equals(firstValue);
      });

      //
   }

   @Test
   void callGetOnlyOnce_NonZeroTtl_DontWait() {
      // Given
      BiFunction<String, String, String> biFunction = new TestValueBiFunction();
      TtlLazyValueBiFunction<String, String, String> stringTtlLazyValueBiFunction = new TtlLazyValueBiFunction<>(900_000, biFunction);

      // When
      String firstValue = stringTtlLazyValueBiFunction.apply("t1", "t2");
      String secondValue = stringTtlLazyValueBiFunction.apply("t1", "t2");

      // When
      assertThat(firstValue).isEqualTo(secondValue);
   }

   @Test
   void callGetOnlyOnce_NonZeroTtl_DontWait_DifferentInputAndDifferentOutput() {
      // Given
      BiFunction<String, String, String> biFunction = new TestValueBiFunction();
      TtlLazyValueBiFunction<String, String, String> stringTtlLazyValueBiFunction = new TtlLazyValueBiFunction<>(900_000, biFunction);

      // When, calling twice (within the time-to-life range) but with different input-values -> we are expecting that the returned value is different!
      String firstValue = stringTtlLazyValueBiFunction.apply("t1", "t2");
      String secondValue = stringTtlLazyValueBiFunction.apply("t3", "t4");

      // When
      assertThat(firstValue).isNotEqualTo(secondValue);
   }

   @Test
   void callGetOnlyOnce_NonZeroTtl_ButForceApply() {
      // Given
      BiFunction<String, String, String> biFunction = new TestValueBiFunction();
      TtlLazyValueBiFunction<String, String, String> stringTtlLazyValueBiFunction = new TtlLazyValueBiFunction<>(900_000, biFunction);

      // When
      String firstValue = stringTtlLazyValueBiFunction.apply("t1", "t2");
      String secondValue = stringTtlLazyValueBiFunction.applyForced("t1", "t2");

      // When
      assertThat(firstValue).isNotEqualTo(secondValue);
   }

   private static class TestValueBiFunction implements BiFunction<String, String, String> {

      @Override
      public String apply(String s, String s2) {
         return s + Math.random() + s2;
      }
   }
}
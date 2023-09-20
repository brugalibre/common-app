package com.brugalibre.util.hashkey;

import java.util.Arrays;
import java.util.Objects;

/**
 * A HashKey is a combined hash-value for multiple input values
 *
 * @param hashValue
 */
public record HashKey(int hashValue) {
   public static HashKey of(Object... objects) {
      Integer hashValue1 = Arrays.stream(objects)
              .filter(Objects::nonNull)
              .map(Object::hashCode)
              .reduce(Integer::sum)
              .orElse(-1);
      return new HashKey(hashValue1);
   }
}

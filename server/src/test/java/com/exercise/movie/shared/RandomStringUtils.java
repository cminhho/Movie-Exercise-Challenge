package com.exercise.movie.shared;

import java.util.Random;

public final class RandomStringUtils {

  public static String randomAlphanumeric(int targetStringLength) {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = leftLimit + (int)
          (random.nextFloat() * (rightLimit - leftLimit + 1));
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }
}


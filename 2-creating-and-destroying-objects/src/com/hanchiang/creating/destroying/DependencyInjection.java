package com.hanchiang.creating.destroying;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A very common pattern when dealing with classes that contains dependency, which is the case for
 * many classes.
 * This allows us to work with an arbitrary number of resources, providing flexibility
 * in our code.
 */
public class DependencyInjection {}

class SpellChecker {
  private final Map<String, String> dictionary;

  public SpellChecker(Map<String, String> dictionary) {
    this.dictionary = Objects.requireNonNull(dictionary);
  }

  public boolean isValid(String word) {
      return true;
  }

  public List<String> suggestions(String typo) {
      return new ArrayList<>();
  }
}

package com.hanchiang.creating.destroying;

import java.util.regex.Pattern;

/**
 * Avoid unnecessary creation of objects because this can greatly reduce the performance
 * of your application.
 * You can often avoid creating unnecessary objects by using static factory methods in preference to constructors.
 *
 * Examples: String, Pattern, boxed
 */
public class UnnecessaryObjectCreation {
  private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  private static final int ITERATIONS = 1000;

  public static void main(String[] args) {
    runStringExample();
    runRegexExample();
    runSumExample();
  }

  public static void runStringExample() {
    long badStringTimeTaken = 0;
    long goodStringTimeTaken = 0;

    for (int i = 0 ; i < ITERATIONS; i++) {
      long before = System.nanoTime();
      badStringExample();
      long after = System.nanoTime();
      badStringTimeTaken += after - before;
    }

    for (int i = 0 ; i < ITERATIONS; i++) {
      long before = System.nanoTime();
      goodStringExample();
      long after = System.nanoTime();
      goodStringTimeTaken += after - before;
    }

    // Speed up: ~6
    System.out.println("Time taken(ns) to execute badStringExample(): " + badStringTimeTaken);
    System.out.println("Time taken(ns) to execute goodStringExample(): " + goodStringTimeTaken);
  }

  // If this is called frequently, many objects will be created, which is unnecessary
  public static void badStringExample() {
    String bad = new String("bad");
  }

  // Even if this is called frequently, only a single String instance is created
  public static void goodStringExample() {
    String good = "good";
  }

  public static void runRegexExample() {
    String regexInput = "1234567";
    long badRegexTimeTaken = 0;
    long goodRegexTimeTaken = 0;

    for (int i = 0 ; i < ITERATIONS; i++) {
      long before = System.nanoTime();
      badRegexExample(regexInput);
      long after = System.nanoTime();
      badRegexTimeTaken += after - before;
    }

    for (int i = 0 ; i < ITERATIONS; i++) {
      long before = System.nanoTime();
      goodRegexExample(regexInput);
      long after = System.nanoTime();
      goodRegexTimeTaken += after - before;
    }

    // speed up: ~13
    System.out.println("Time taken(ns) to execute badRegexExample(): " + badRegexTimeTaken);
    System.out.println("Time taken(ns) to execute goodRegexExample(): " + goodRegexTimeTaken);
  }

  /**
   * A Pattern instance for the regular expression and uses it only once,
   * after which it becomes eligible for garbage collection, resulting in poor performance
   * if this function is invoked frequently
   */
  public static boolean badRegexExample(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  }

  /**
   * Explicitly compile the regular expression into a Pattern instance (which is immutable)
   * as part of class initialization, cache it, and reuse the same instance for every invocation,
   * resulting in significant performance gains if this function is invoked frequently
   */
  public static boolean goodRegexExample(String s) {
    return ROMAN.matcher(s).matches();
  }

  public static void runSumExample() {
    long badSumTimeTaken = 0;
    long goodSumTimeTaken = 0;

    for (int i = 0 ; i < ITERATIONS; i++) {
      long before = System.nanoTime();
      badSumExample();
      long after = System.nanoTime();
      badSumTimeTaken += after - before;
    }

    for (int i = 0 ; i < ITERATIONS; i++) {
      long before = System.nanoTime();
      goodSumExample();
      long after = System.nanoTime();
      goodSumTimeTaken += after - before;
    }

    // speed up: ~4
    System.out.println("Time taken(ns) to execute badSumExample(): " + badSumTimeTaken);
    System.out.println("Time taken(ns) to execute goodSumExample(): " + goodSumTimeTaken);
  }

  /**
   * The performance killer is the declaration of Long.
   * A Long will be created in each iteration of the loop
   */
  public static long badSumExample() {
    Long sum = 0L;
    for (long i = 0; i <= ITERATIONS; i++) {
      sum += i;
    }
    return sum;
  }

  public static long goodSumExample() {
    long sum = 0L;
    for (long i = 0; i <= ITERATIONS; i++) {
      sum += i;
    }
    return sum;
  }
}
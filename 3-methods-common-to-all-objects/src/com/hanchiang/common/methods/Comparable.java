package com.hanchiang.common.methods;

import java.util.Objects;

/**
 * Consider implementing Comparable.
 * Unlike the other methods discussed in this chapter, the compareTo method is not declared in Object.
 * By implementing Comparable, a class indicates that its instances have a natural ordering.
 *
 * By implementing Comparable, you allow your class to interoperate with
 * all the many generic algorithms and collection implementations that depend on this interface.
 * You gain a tremendous amount of power for a small amount of effort.
 *
 * The general contract of the compareTo method is similar to that of equal.
 *
 * Just as a class that violates the hashCode contract can break other classes that depend on hashing,
 * a class that violates the compareTo contract can break other classes that depend on comparison.
 * Classes that depend on comparison include the sorted collections TreeSet and TreeMap
 * and the utility classes Collections and Arrays, which contain searching and sorting algorithms.
 *
 * Avoid the use of the < and > operators. Instead, use the static compare methods.
 *
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
 */
public class Comparable {

  public static void main(String[] args) {
    CaseInsensitiveString cis = new CaseInsensitiveString("a");
    CaseInsensitiveString cis2 = new CaseInsensitiveString("b");

    // true
    System.out.println(cis.compareTo(cis2) == -1);
    // true
    System.out.println(cis2.compareTo(cis) == 1);
  }

  static class CaseInsensitiveString implements java.lang.Comparable<CaseInsensitiveString> {
    String s;

    public CaseInsensitiveString(String s) {
      this.s = Objects.requireNonNull(s);
    }

    public int compareTo(CaseInsensitiveString cis) {
      return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
    }
  }

}

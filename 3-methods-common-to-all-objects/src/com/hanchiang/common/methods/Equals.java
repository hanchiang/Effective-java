package com.hanchiang.common.methods;

import java.awt.*;
import java.util.Objects;

/**
 * Obey the general contract when overriding equals
 * Overriding the equals method seems simple, but there are many ways to get it wrong, and consequences can be dire.
 *
 * Do not override equals if any of the following conditions apply:
 * - Each instance of the class is inherently unique
 * - There is no need for the class to provide a logical equality test, e.g. java.util.regex.Pattern
 * - A super class has already overridden equals, and the super class behavior is appropriate for this class,
 * e.g. ost Set implementations inherit their equals implementation from AbstractSet,
 * List implementations from AbstractList, and Map implementations from AbstractMap.
 * - The class is private or package-private, and you are certain that its equals method will never be invoked
 *
 * When is it appropriate to override equals?
 * When a class has a notion of logical equality that differs from mere object identity
 * and a superclass has not already overridden equals.
 * This is generally the case for value classes(e.g. Integer, String).
 *
 * Contract: reflexive, symmetric, transitive, consistent, non-null
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-
 *
 * Writing and testing equals (and hashCode) methods is tedious, and the resulting code is mundane.
 * An excellent alternative to writing and testing these methods manually is to use Google’s open source AutoValue framework,
 * which automatically generates these methods for you.
 */
public class Equals {
  public static void main(String[] args) {
    runSymmetricViolation();
  }

  public static void runSymmetricViolation() {
    SymmetryViolation sv = new SymmetryViolation("Singapore");
    String s = "singapore";

    // true
    System.out.println(sv.equals(s));
    // true? false?
    System.out.println(s.equals(sv));
  }
}

class SymmetryViolation {
  private final String s;

  public SymmetryViolation(String s) {
    this.s = Objects.requireNonNull(s);
  }

  public static void main(String[] args) {
    SymmetryViolation sv = new SymmetryViolation("hi");
    String s = "hi";

    // true
    System.out.println(sv.equals(sv));
    // false. Symmetry violation!
    System.out.println(s.equals(sv));
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SymmetryViolation) {
      return s.equalsIgnoreCase(((SymmetryViolation)o).s);
    }
    // One-way interoperability only!
    if (o instanceof String) {
      return s.equalsIgnoreCase((String) o);
    }
    return false;
  }
}

/**
 * There is no way to extend an instantiable class and add a value component while preserving the equals contract.
 * Example: java.sql.Timestamp extends java.util.Date and adds a nanoseconds field.
 * The equals implementation violate symmetry
 *
 * A workaround: Use composition instead of inheritance
 * Note that you can add a value component to a subclass of an abstract class without violating the equals contract,
 * because there is no way to instantiate the abstract class and do derivedClass.equals(abstractClass)
 */
class TransitivityViolation {
  public static void main(String[] args) {
    ColorPoint p1 = new ColorPoint(1, 2, "Red");
    Point p2 = new Point(1, 2);
    ColorPoint p3 = new ColorPoint(1, 2, "Blue");

    // true
    System.out.println(p1.equals(p2));
    // true
    System.out.println(p2.equals(p3));
    // false! Transitivity violation!
    System.out.println(p1.equals(p3));
  }

  static class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Point)) {
        return false;
      }

      Point p = (Point)o;
      return p.x == x && p.y == y;
    }
  }

  // Suppose you want to extend this class, adding the notion of color to a point
  static class ColorPoint extends Point {
    private final String color;

    public ColorPoint(int x, int y, String color) {
      super(x, y);
      this.color = color;
    }

    /**
     * Broken - violates symmetry!
     * point.equals(colorPoint) returns true while colorPoint.equals(point) returns false
     */
//    @Override
//    public boolean equals(Object o) {
//      if (!(o instanceof ColorPoint)) {
//        return false;
//      }
//      return super.equals(o) && ((ColorPoint) o).color == color;
//    }

    // Broken - violates transitivity!
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Point)) {
        return false;
      }

      // If o is a normal Point, do a color-blind comparison
      if (!(o instanceof ColorPoint)) {
        return o.equals(this);
      }

      return super.equals(o) && ((ColorPoint)o).color == color;
    }
  }
}
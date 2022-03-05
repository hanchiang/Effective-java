package com.hanchiang.creating.destroying;

/**
 * Providing a static factory method instead of a public constructor has both advantages and
 * disadvantages.
 *  + Static factory with a well-chosen name is easier to use and the resulting client code easier to read.
 *  + Unlike constructors, they are not required to create a new object each time they’re invoked.
 *    The ability of static factory methods to return the same object from repeated invocations allows
 *    classes to maintain strict control over what instances exist at any time.
 *  + Unlike constructors, they can return an object of any subtype of their return type.
 *    --> Encapsulation
 *  - Classes without public or protected constructors cannot be subclassed. This encourages composition instead of inheritance.
 *  - Hard to find, because they don't stand out in the sea of API documentations.
 */
public class StaticFactoryMethod {

  public static void main(String[] args) {
    Boolean a = StaticFactoryMethod.valueOf(true);
  }

  public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
  }
}

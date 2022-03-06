package com.hanchiang.creating.destroying;

/**
 * A singleton is simply a class that is instantiated exactly once.
 * It typically represents either a stateless object such as a function or a system component that is intrinsically unique.
 * + Ensure only one instance of a Class is created
 * - Can make it difficult to test its clients because it's impossible to substitute a mock implementation
 * for a singleton unless it implements an interface
 *
 */
public class Singleton {
  private static final Singleton INSTANCE = new Singleton();

  private Singleton() {}

  public static Singleton getInstance() {
    return INSTANCE;
  }

  public void leaveTheBuilding() {}

  public static void main(String[] args) {
    Singleton.getInstance().leaveTheBuilding();
    SingletonEnum.INSTANCE.leaveTheBuilding();
  }
}

/**
 * Preferred method
 * Serialisation comes for free
 */
enum SingletonEnum {
    INSTANCE;

    public void leaveTheBuilding() {}
}
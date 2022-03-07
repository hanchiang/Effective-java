package com.hanchiang.creating.destroying;

import java.lang.ref.Cleaner;

/**
 * Finalizers are unpredictable, often dangerous, and generally unnecessary. Their use can cause
 * erratic behavior, poor performance, and portability problems.
 * Cleaners are less dangerous than finalizers, but still unpredictable, slow, and generally unnecessary.
 * - One shortcoming of finalizers and cleaners is that there is no guarantee they’ll be executed promptly.
 * It can take arbitrarily long between the time that an object becomes unreachable and the time its finalizer
 * or cleaner runs.
 * - The promptness with which finalizers and cleaners are executed is primarily a
 * function of the garbage collection algorithm, which varies widely across implementations.
 * - Another problem with finalizers is that an uncaught exception thrown during finalization is
 * ignored, and finalization of that object terminates. - There is a severe performance penalty for
 * using finalizers and cleaners.
 *
 * So, what to do? Have your classes implement AutoCloseable, and require its clients invoke the
 * close method when it is no longer needed.
 *
 * What are finalizers and cleaners good for?
 * + Act as a safety net in case the owner of a resource neglects to call its close method.
 * + Use it to reclaim native peers(non-java) object.
 */
public class FinalizerAndCleaner {

  public static void main(String[] args) {
      /**
       * A try-with-resources statement is a try statement that declares one or more resources.
       * A resource is an object that must be closed after the program is finished with it.
       * The try-with-resources statement ensures that each resource is closed at the end of the statement.
       * Any object that implements java.lang.AutoCloseable, which includes all objects which implement java.io.Closeable,
       * can be used as a resource.
       * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
       */
    try (Room myRoom = new Room(7)) {
        System.out.println("Goodbye");
    }
  }

  static class Room implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();

    // The state of this room, shared with our cleanable
    private final State state;

    // Our cleanable. Cleans the room when it’s eligible for gc
    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
      state = new State(numJunkPiles);
      cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() {
      cleanable.clean();
    }

    // Resource that requires cleaning. Must not refer to Room!
    // State must be a static nested class because nonstatic nested classes contain references to
    // their enclosing instances
    private static class State implements Runnable {
      int numJunkPiles; // Number of junk piles in this room

      State(int numJunkPiles) {
        this.numJunkPiles = numJunkPiles;
      }

      // Invoked by close method or cleaner
      @Override
      public void run() {
        System.out.println("Cleaning room");
        numJunkPiles = 0;
      }
    }
  }
}

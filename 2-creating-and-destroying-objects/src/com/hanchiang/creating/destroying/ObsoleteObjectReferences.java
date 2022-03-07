package com.hanchiang.creating.destroying;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Objects that are not accessible anymore but still live in memory will cause memory leak.
 * This results in reduced performance. In worst case, the program will fail with a OutOfMemoryError error.
 * Generally speaking, whenever a class manages its own memory,
 * the programmer should be alert for memory leaks.
 * Another common source of memory leaks is caches. Once you put an object reference into a cache,
 * it’s easy to forget that it’s there and leave it in the cache long after it becomes irrelevant.
 * A third common source of memory leaks is listeners and other callbacks.
 * One solution is to store weak references(WeakHashMap) so that they can be garbage collected.
 */
public class ObsoleteObjectReferences {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(Integer.valueOf(1));
        stack.push(Integer.valueOf(2));
        stack.safePop();
        stack.pop();
    }

    static class Stack {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object o) {
            ensureCapacity();
            elements[size++] = o;
        }

        /**
         * Memory leak! Objects that were popped off the stack will not be garbage collected
         * because their references are still present, but  they won't be dereferenced again.
         */
        public Object pop() {
            if (size == 0) {
                throw new EmptyStackException();
            }
            return elements[--size];
        }

        public Object safePop() {
            if (size == 0) {
                throw new EmptyStackException();
            }
            Object result = elements[--size];
            // Remove obsolete reference. No more memory leak!
            elements[size] = null;
            return result;
        }

        /**
         * Ensure space for at least one more element, roughly
         * doubling the capacity each time the array needs to grow.
         */
        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}

package com.hanchiang.common.methods;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Override clone judiciously.
 * The Cloneable interface has no methods. It determines the behavior of Object’s protected clone implementation:
 * if a class implements Cloneable, Object’s clone method returns a field-by-field copy of the object;
 * otherwise it throws CloneNotSupportedException. This is a highly atypical use of interfaces and not one to be emulated.
 *
 * If the superclass provides a clone method, use it.
 *
 * Be careful when an object contain fields that refer to mutable objects.
 * Those objects need to be cloned recursively.
 *
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#clone()
 */
public class Clone {

  public static void main(String[] args) {
    cloneWithNoReferenceToMutableState();
    cloneWithReferenceToMutableState();
    cloneWithReferenceToMutableState2();
  }

  public static void cloneWithNoReferenceToMutableState() {
    PhoneNumber pn = new PhoneNumber(65, 0, 999);
    PhoneNumber clone = pn.clone();

    // true
    System.out.println(pn != clone);
    // true
    System.out.println(pn.equals(clone));
    // true
    System.out.println(clone.equals(pn));
  }

  public static void cloneWithReferenceToMutableState() {
    Stack s = new Stack();
    s.push(1);
    s.push(2);

    Stack clone = s.clone();
    // true
    System.out.println(s != clone);
    // true
    System.out.println(s.equals(clone));
    // true
    System.out.println(clone.equals(s));
  }

  public static void cloneWithReferenceToMutableState2() {
    HashTable ht = new HashTable();
    HashTable clone = ht.clone();

    // true
    System.out.println(ht != clone);
    // true
    System.out.println(ht.equals(clone));
    // true
    System.out.println(clone.equals(ht));
  }
}

class PhoneNumber implements Cloneable {
  private final short areaCode, prefix, lineNum;

  public PhoneNumber(int areaCode, int prefix, int lineNum) {
    this.areaCode = rangeCheck(areaCode, 999, "area code");
    this.prefix = rangeCheck(prefix, 999, "prefix");
    this.lineNum = rangeCheck(lineNum, 9999, "line num");
  }

  private static short rangeCheck(int val, int max, String arg) {
    if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
    return (short) val;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof PhoneNumber)) return false;
    PhoneNumber pn = (PhoneNumber) o;

    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  @Override
  public int hashCode() {
    int result = Short.hashCode(areaCode);
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
  }

  // Clone method for class with no references to mutable state
  @Override public PhoneNumber clone() {
    try {
      return (PhoneNumber) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}

class Stack implements Cloneable {
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0) throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null; // Eliminate obsolete reference
    return result;
  }

  // Ensure space for at least one more element.
  private void ensureCapacity() {
    if (elements.length == size) elements = Arrays.copyOf(elements, 2 * size + 1);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Stack)) {
      return false;
    }
    if (((Stack) o).size != this.size) {
      return false;
    }
    for (int i = 0; i < this.size; i++) {
      if (!this.elements[i].equals(((Stack) o).elements[i])) {
        return false;
      }
    }
    return true;
  }

  @Override public int hashCode() {
    return Arrays.hashCode(this.elements);
  }

  /**
   * If the clone method merely returns super.clone(), the resulting Stack instance will have the correct value in
   * its size field, but its elements field will refer to the same array as the original Stack instance.
   * Modifying the original will destroy the invariants in the clone and vice versa.
   */
  @Override
  public Stack clone() {
    try {
      Stack result = (Stack) super.clone();
      // Important: References to mutable state need to be cloned
      result.elements = elements.clone();
      return result;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}

/**
 * It is not always sufficient merely to call clone recursively. For example, suppose you are
 * writing a clone method for a hash table whose internals consist of an array of buckets, each of
 * which references the first entry in a linked list of key-value pairs.
 */
class HashTable implements Cloneable {
  private Entry[] buckets;
  private static final int INITIAL_CAPACITY = 10;

  public HashTable() {
    this.buckets = new Entry[INITIAL_CAPACITY];

    this.buckets[0] = new Entry(1, 2, null);
    this.buckets[0].next = new Entry(3, 4, null);
  }

  private static class Entry {
    final Object key;
    Object value;
    Entry next;

    Entry(Object key, Object value, Entry next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }

    // Recursively copy the linked list headed by this Entry
    public Entry deepCopy() {
      return new Entry(key, value, next == null ? null : next.deepCopy());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof HashTable)) {
      return false;
    }
    if (((HashTable) o).buckets.length != this.buckets.length) {
      return false;
    }
    for (int i = 0; i < this.buckets.length; i++) {
      if ((this.buckets[i] == null && ((HashTable) o).buckets[i] != null)
        || (((HashTable) o).buckets[i] == null && this.buckets[i] != null)) {
        return false;
      }

      Entry thisElement = this.buckets[i];
      Entry otherElement = ((HashTable) o).buckets[i];

      while(thisElement != null && otherElement != null) {
        if ((thisElement.key != otherElement.key)
        || (thisElement.value != otherElement.value)) {
          return false;
        }

        thisElement = thisElement.next;
        otherElement = otherElement.next;
      }

      if (thisElement != otherElement) {
        return false;
      }
    }
    return true;
  }

  /**
   * Broken clone method - results in shared mutable state!
   * Though the clone has its own bucket array, this array references the same linked lists
   * as the original, which can easily cause nondeterministic behavior in both the clone and the original.
   */
//  @Override
//  public HashTable clone() {
//    try {
//      HashTable result = (HashTable) super.clone();
//      result.buckets = buckets.clone();
//      return result;
//    } catch (CloneNotSupportedException e) {
//      throw new AssertionError();
//    }
//  }


  @Override
  public HashTable clone() {
    try {
      HashTable result = (HashTable) super.clone();
      result.buckets = new Entry[buckets.length];
      for (int i = 0; i < buckets.length; i++) {
        if (buckets[i] != null) {
          result.buckets[i] = buckets[i].deepCopy();
        }
      }
      return result;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}

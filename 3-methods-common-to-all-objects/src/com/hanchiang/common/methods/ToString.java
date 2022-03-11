package com.hanchiang.common.methods;

/**
 * Always override toString
 * While Object provides an implementation of the toString method,
 * the string that it returns is generally not what the user of your class wants to see.
 * It consists of the class name followed by an “at” sign (@) and the unsigned hexadecimal representation
 * of the hash code, for example, PhoneNumber@163b91.
 *
 * While it isn’t as critical as obeying the equals and hashCode contracts,
 * providing a good toString implementation makes your class much more pleasant to use
 * and makes systems using the class easier to debug.
 *
 * The toString method is automatically invoked when an object is passed to println,
 * printf, the string concatenation operator, or assert, or is printed by a debugger.
 *
 * Google’s open source AutoValue facility will generate a toString method for you.
 */
public class ToString {
  public static void main(String[] args) {
    PhoneNumber pn = new PhoneNumber(1, 22, 333);
    System.out.println(pn);
  }

  static class PhoneNumber {
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

    /**
     * Returns the string representation of this phone number.
     * The string consists of twelve characters whose format is
     * "XXX-YYY-ZZZZ", where XXX is the area code, YYY is the
     * prefix, and ZZZZ is the line number. Each of the capital
     * letters represents a single decimal digit.
     *
     * If any of the three parts of this phone number is too small
     * to fill up its field, the field is padded with leading zeros.
     * For example, if the value of the line number is 123, the last * four characters of the string representation will be "0123". */
    @Override
    public String toString() {
      return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
    }
  }
}


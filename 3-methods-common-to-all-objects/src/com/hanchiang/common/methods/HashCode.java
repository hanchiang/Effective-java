package com.hanchiang.common.methods;

/**
 * Always override hashCode when you override equals
 * If you fail to do so, your class will violate the general contract for hashCode,
 * which will prevent it from functioning properly in collections such as HashMap and HashSet.
 *
 * Important: Equal objects must have equal hash codes
 *
 * Recipe for a good hash function:
 * - Declare an int variable named result, and initialize it to the hash code c for the first significant field(affects equals comparison)
 * in your object
 * - For every remaining significant field f in your object, do the following:
 *  - Compute an int hash code c for the field:
 *    - If the field is of a primitive type, compute Type.hashCode(f),
 *    where Type is the boxed primitive class corresponding to f’ s type.
 *    - If the field is an object reference and this class’s equals method compares the field by recursively invoking equals,
 *    recursively invoke hashCode on the field.
 *    If a more complex comparison is required, compute a “canonical representation” for this field and invoke hashCode on the canonical representation.
 *    If the value of the field is null, use 0.
 *    - If the field is an array, treat it as if each significant element were a separate field.
 *    If the array has no significant elements, use a constant, preferably not 0. If all elements are significant, use Arrays.hashCode.
 *  - Combine the hash code c computed in the step above into result as follows:
 *    result = 31 * result + c;
 *
 *
 * The multiplication in makes the result depend on the order of the fields,
 * yielding a much better hash function if the class has multiple similar fields.
 * The value 31 was chosen because it is an odd prime.
 * - Information will not be lost if there is an overflow
 * - Multiplication can be replaced by bit shift and subtraction: 31 * i == (i << 5) - i
 *
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode()
 *
 *
 * If you have a bona fide need for hash functions less likely to produce collisions, see Guava’s com.google.common.hash.Hashing.
 */
public class HashCode {

  static class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
      this.areaCode = rangeCheck(areaCode, 999, "area code");
      this.prefix   = rangeCheck(prefix,   999, "prefix");
      this.lineNum  = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) { if (val < 0 || val > max)
      throw new IllegalArgumentException(arg + ": " + val); return (short) val;
    }

    @Override public boolean equals(Object o) { if (o == this)
      return true;
      if (!(o instanceof PhoneNumber))
        return false;
      PhoneNumber pn = (PhoneNumber)o;
      return pn.lineNum == lineNum && pn.prefix == prefix
              && pn.areaCode == areaCode;
    }

    @Override public int hashCode() {
      int result = Short.hashCode(areaCode);
      result = 31 * result + Short.hashCode(prefix);
      result = 31 * result + Short.hashCode(lineNum);
      return result;
    }
  }
}

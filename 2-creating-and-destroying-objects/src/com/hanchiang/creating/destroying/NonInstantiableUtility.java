package com.hanchiang.creating.destroying;

/**
 * Occasionally you’ll want to write a class that is just a grouping of static methods and static fields.
 * Such utility classes were not designed to be instantiated.
 * However, the compiler provides a public, parameterless default constructor.
 * So, we have to explicitly define a private constructor.
 */
public class NonInstantiableUtility {
    // some static fields

    private NonInstantiableUtility() {}

    // some static methods
}

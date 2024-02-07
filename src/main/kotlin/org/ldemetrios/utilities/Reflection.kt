package org.ldemetrios.utilities

fun Class<*>.superclassList(): List<Class<*>> {
    val res = mutableListOf<Class<*>>(this)
    var clazz: Class<*>? = this
    do {
        clazz = clazz?.superclass
        if (clazz != null) res.add(clazz)
    } while (clazz != Any::class.java && clazz != null)
    return res
}

fun juniorestCommonSuperclass(c1: Class<*>, c2: Class<*>): Class<*>? {
    if (c1 == Void::class.java) return c2
    if (c2 == Void::class.java) return c1

    val super1 = c1.superclassList()
    val super2 = c2.superclassList()
    for (clazz in super1) if (clazz in super2) return clazz
    return null //Stub, will never be reached
}
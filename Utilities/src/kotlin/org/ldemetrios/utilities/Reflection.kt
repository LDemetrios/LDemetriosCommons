package org.ldemetrios.utilities

fun Class<*>.superclassList(): List<Class<*>> {
    val res = mutableListOf<Class<*>>(this)
    var clazz = this
    do {
        clazz = clazz.superclass
        res.add(clazz)
    } while (clazz != Any::class.java)
    return res
}

fun juniorestCommonSuperclass(c1: Class<*>, c2: Class<*>): Class<*> {
    val super1 = c1.superclassList()
    val super2 = c2.superclassList()
    for (clazz in super1) if (clazz in super2) return clazz
    return Any::class.java //Stub, will never be reached
}
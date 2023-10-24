package org.ldemetrios.js

inline fun <reified T> Any?.castOrNull(): T? = if (this is T) this else null
inline fun <reified T> Any?.cast(): T = this as T

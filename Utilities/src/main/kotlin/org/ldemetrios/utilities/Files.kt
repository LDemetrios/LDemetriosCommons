@file:Suppress("PackageDirectoryMismatch", "unused")
@file:JvmName("Files")

package org.ldemetrios.utils.files

import java.io.*

fun File.recreate() {
    if (exists()) delete()
    forcefullyCreate()
}

fun File.forcefullyCreate() {
    if (exists()) throw IOException("File already exists")
    try {
        createNewFile()
    } catch (e: IOException) {
        File(absolutePath.split("/").dropLast(1).joinToString("/")).mkdirs()
        createNewFile()
    }
}

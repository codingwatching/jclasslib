/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license or (at your option) any later version.
*/
package org.gjt.jclasslib.structures.attributes

import org.gjt.jclasslib.io.DataInput
import org.gjt.jclasslib.io.DataOutput
import org.gjt.jclasslib.structures.AttributeInfo
import org.gjt.jclasslib.structures.ClassFile
import org.gjt.jclasslib.structures.emptyArraySingleton

/**
 * Common class for runtime type annotations.

 */
abstract class RuntimeTypeAnnotationsAttribute(classFile: ClassFile) : AttributeInfo(classFile), AnnotationHolder {

    /**
     * Runtime annotations associations of the parent structure
     */
    var runtimeAnnotations: Array<TypeAnnotation> = emptyArraySingleton()

    override fun readData(input: DataInput) {
        val runtimeVisibleAnnotationsLength = input.readUnsignedShort()
        runtimeAnnotations = Array(runtimeVisibleAnnotationsLength) {
            TypeAnnotation(input)
        }
    }

    override fun writeData(output: DataOutput) {
        output.writeShort(runtimeAnnotations.size)
        runtimeAnnotations.forEach { it.write(output) }
    }

    override val debugInfo: String
        get() = "with ${runtimeAnnotations.size} entries"

    override fun getAttributeLength(): Int = 2 + runtimeAnnotations.sumOf { it.length }

    override val numberOfAnnotations: Int
        get() = runtimeAnnotations.size

}

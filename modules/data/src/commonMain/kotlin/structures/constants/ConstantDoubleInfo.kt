/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants

import org.gjt.jclasslib.io.DataOutput
import org.gjt.jclasslib.structures.ClassFile
import org.gjt.jclasslib.structures.ConstantType

/**
 * Describes a CONSTANT_Double_info constant pool data structure.
 */
class ConstantDoubleInfo(classFile: ClassFile) : ConstantLargeNumeric(classFile) {

    override val constantType: ConstantType
        get() = ConstantType.DOUBLE

    override val verbose: String
        get() = double.toString()

    /**
     * The double value of this constant pool entry.
     */
    var double: Double
        get() {
            val longBits = highBytes.toLong() shl 32 or (lowBytes.toLong() and 0xFFFFFFFFL)
            return Double.fromBits(longBits)
        }
        set(number) {
            val longBits = if (number.isNaN())  0x7ff8000000000000L else number.toRawBits()
            highBytes = (longBits ushr 32 and 0xFFFFFFFFL).toInt()
            lowBytes = (longBits and 0xFFFFFFFFL).toInt()
        }

    override fun writeData(output: DataOutput) {
        output.writeByte(ConstantType.DOUBLE.tag)
        super.writeData(output)
    }

}

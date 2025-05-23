/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants

import org.gjt.jclasslib.io.DataInput
import org.gjt.jclasslib.io.DataOutput
import org.gjt.jclasslib.structures.AbstractConstant
import org.gjt.jclasslib.structures.ClassFile
import org.gjt.jclasslib.structures.InvalidByteCodeException

/**
 * Base class for constant pool data structures which reference class members.
 */
abstract class ConstantReference(classFile: ClassFile) : AbstractConstant(classFile) {

    /**
     * Index of the constant pool entry containing the
     * CONSTANT_Class_info of this entry.
     */
    var classIndex: Int = 0

    /**
     * Index of the constant pool entry containing the
     * CONSTANT_NameAndType_info of this entry.
     */
    var nameAndTypeIndex: Int = 0

    override val verbose: String
        get() = classConstant.verbose + "." + nameAndTypeConstant.verbose

    /**
     * Class info for this reference.
     */
    val classConstant: ConstantClassInfo
        get() = classFile.getConstantPoolEntry(classIndex, ConstantClassInfo::class)

    /**
     * Name and type info for this reference.
     * @throws InvalidByteCodeException
     */
    val nameAndTypeConstant: ConstantNameAndTypeInfo
        get() = classFile.getConstantPoolEntry(nameAndTypeIndex, ConstantNameAndTypeInfo::class)

    override fun readData(input: DataInput) {
        classIndex = input.readUnsignedShort()
        nameAndTypeIndex = input.readUnsignedShort()
    }

    override fun writeData(output: DataOutput) {
        output.writeShort(classIndex)
        output.writeShort(nameAndTypeIndex)
    }

    override val debugInfo: String
        get() = "with classIndex $classIndex and nameAndTypeIndex $nameAndTypeIndex"

    override fun equals(other: Any?): Boolean {
        if (other !is ConstantReference) {
            return false
        }
        return super.equals(other) && other.classIndex == classIndex && other.nameAndTypeIndex == nameAndTypeIndex
    }

    override fun hashCode(): Int = super.hashCode() xor classIndex xor nameAndTypeIndex

}

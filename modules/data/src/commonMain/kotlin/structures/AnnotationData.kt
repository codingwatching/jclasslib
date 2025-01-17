/*
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public
 License as published by the Free Software Foundation; either
 version 2 of the license or (at your option) any later version.
 */

package org.gjt.jclasslib.structures

import org.gjt.jclasslib.structures.elementvalues.ElementValuePair

/**
 * Base class for annotation content.
 */
interface AnnotationData {
    /**
     * Element value pair associations of the parent
     * structure
     */
    var elementValuePairEntries: Array<ElementValuePair>

    /**
     * type_index of this annotation.
     */
    var typeIndex: Int
}

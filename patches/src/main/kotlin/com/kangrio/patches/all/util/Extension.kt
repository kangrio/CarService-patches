package com.kangrio.patches.all.util

import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.StringReference

fun Instruction.replacementOrNull(mapping: Map<String, String>): String? =
    ((this as? ReferenceInstruction)?.reference as? StringReference)
        ?.string
        ?.let(mapping::get)
package com.kangrio.patches.all.misc.automotive.maps

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.patch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.kangrio.patches.all.misc.Constant.COMPATIBILITY_MAPS_AUTOMOTIVE
import com.kangrio.patches.all.misc.automotive.carServicePatch

private val fingerPrint = Fingerprint(
    returnType = "V",
    name = "onCreate",
    parameters = emptyList(),
    custom = { method, _ ->
        method.implementation?.instructions?.any { insn ->
            val ref = (insn as? ReferenceInstruction)?.reference as? MethodReference
            insn.opcode == Opcode.INVOKE_STATIC &&
                    ref?.definingClass == "Ljava/lang/System;" &&
                    ref.name == "exit" &&
                    ref.returnType == "V" &&
                    ref.parameterTypes == listOf("I")
        } == true
    }
)

@Suppress("unused")
val fixMapsSelfKill = patch {
    execute {
        fingerPrint.matchOrNull()?.let { match ->
            val superClass = match.classDef.superclass

            match.method.addInstructions(
                0,
                """
                    invoke-super {p0}, $superClass->onCreate()V
                    return-void
                """.trimIndent()
            )
        }
    }
}
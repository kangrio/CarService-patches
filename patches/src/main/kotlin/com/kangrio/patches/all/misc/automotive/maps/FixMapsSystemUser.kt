package com.kangrio.patches.all.misc.automotive.maps

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.patch.patch
import app.morphe.patcher.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.kangrio.patches.all.misc.Constant.COMPATIBILITY_MAPS_AUTOMOTIVE
import com.kangrio.patches.all.misc.automotive.carServicePatch

private val fingerPrint = Fingerprint(
    returnType = "Z",
    parameters = emptyList(),
    strings = listOf("user"),
    custom = { method, _ ->
        method.implementation?.instructions?.any { insn ->
            val ref = (insn as? ReferenceInstruction)?.reference as? MethodReference
            insn.opcode == Opcode.INVOKE_VIRTUAL &&
                    ref?.definingClass == "Landroid/os/UserManager;" &&
                    ref.name == "isSystemUser" &&
                    ref.returnType == "Z" &&
                    ref.parameterTypes.isEmpty()
        } == true
    }
)

@Suppress("unused")
val fixMapsSystemUser = patch {
    execute {
        fingerPrint.matchOrNull()?.method?.returnEarly(true)
    }
}
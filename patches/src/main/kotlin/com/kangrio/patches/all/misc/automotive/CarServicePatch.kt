package com.kangrio.patches.all.misc.automotive

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodeContext
import app.morphe.patcher.patch.classDefForEach
import app.morphe.patcher.patch.intOption
import app.morphe.patcher.patch.mutableClassDefBy
import app.morphe.patcher.patch.patch
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.kangrio.patches.all.misc.automotive.maps.fixMapsSelfKill
import com.kangrio.patches.all.misc.automotive.maps.fixMapsSystemUser
import com.kangrio.patches.all.misc.automotive.maps.replaceMapsProvider
import com.kangrio.patches.all.misc.microg.microGSupportPatch
import com.kangrio.patches.all.util.replacementOrNull

private val PACKAGE_NAME_REPLACE_MAP = mapOf(
    "com.android.car" to "com.kangrio.carserver"
)

@Suppress("unused")
val carServicePatch = patch(
    name = "Car Service",
    description = "Patches for apps to support CarService",
    default = false
) {
    val minSdkVersion = intOption(
        key = "car_service_sdk",
        title = "Car Service SDK",
        description = "Set the Car Service SDK version",
        values = mapOf(
            "29" to 29,
            "32" to 32
        ),
        required = true
    )

    dependsOn(
        compatibilityCheck,
        microGSupportPatch,
        fixMapsSelfKill,
        fixMapsSystemUser,
        replaceMapsProvider
    )

    execute {
        addCarFrameworkSdkPatch(minSdkVersion.value!!).execute(bytecodeContext)
        classDefForEach { classDef ->
            val needsPatch = classDef.methods.any { m ->
                m.implementation?.instructions?.any { it.replacementOrNull(PACKAGE_NAME_REPLACE_MAP) != null } == true
            }
            if (!needsPatch) return@classDefForEach

            mutableClassDefBy(classDef).methods.forEach { method ->
                val impl = method.implementation ?: return@forEach

                val edits = impl.instructions.withIndex().mapNotNull { (index, insn) ->
                    val new = insn.replacementOrNull(PACKAGE_NAME_REPLACE_MAP) ?: return@mapNotNull null
                    Triple(index, (insn as OneRegisterInstruction).registerA, new)
                }

                edits.forEach { (index, reg, new) ->
                    method.replaceInstruction(index, "const-string v$reg, \"${new}\"")
                }
            }
        }
    }
}

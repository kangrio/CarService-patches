package com.kangrio.patches.all.misc.automotive.maps

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.classDefForEach
import app.morphe.patcher.patch.mutableClassDefBy
import app.morphe.patcher.patch.patch
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.kangrio.patches.all.misc.Constant.COMPATIBILITY_MAPS_AUTOMOTIVE
import com.kangrio.patches.all.misc.automotive.carServicePatch
import com.kangrio.patches.all.util.replacementOrNull

private val PROVIDER_REPLACE_MAP = mapOf(
    "google_maps_energy" to "kangrio_google_maps_energy",
    "google_maps_settings" to "kangrio_google_maps_settings",
    "google_maps_assisted_driving" to "kangrio_google_maps_assisted_driving",
    "google_maps_vehicle_profile" to "kangrio_google_maps_vehicle_profile",
)

@Suppress("unused")
val replaceMapsProvider = patch {
    execute {
        classDefForEach { classDef ->
            val needsPatch = classDef.methods.any { m ->
                m.implementation?.instructions?.any { it.replacementOrNull(PROVIDER_REPLACE_MAP) != null } == true
            }
            if (!needsPatch) return@classDefForEach

            mutableClassDefBy(classDef).methods.forEach { method ->
                val impl = method.implementation ?: return@forEach

                val edits = impl.instructions.withIndex().mapNotNull { (index, insn) ->
                    val new = insn.replacementOrNull(PROVIDER_REPLACE_MAP) ?: return@mapNotNull null
                    Triple(index, (insn as OneRegisterInstruction).registerA, new)
                }

                edits.forEach { (index, reg, new) ->
                    method.replaceInstruction(index, "const-string v$reg, \"${new}\"")
                }
            }
        }
    }
}
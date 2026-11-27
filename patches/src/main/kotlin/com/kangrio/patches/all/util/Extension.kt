package com.kangrio.patches.all.util

import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.kangrio.patches.all.misc.Constant.SUPPORTED_APP_SDK_VERSIONS
import com.kangrio.patches.all.misc.Constant.SUPPORTED_CAR_SERVICE_SDK_VERSIONS

fun Instruction.replacementOrNull(mapping: Map<String, String>): String? =
    ((this as? ReferenceInstruction)?.reference as? StringReference)
        ?.string
        ?.let(mapping::get)

fun Int.requireAppMinSdk() = require(this in SUPPORTED_APP_SDK_VERSIONS) {
    "The Application is required min-sdk version is ${SUPPORTED_APP_SDK_VERSIONS.joinToString()}"
}

fun Int.requireCarServiceMinSdk() = require(this in SUPPORTED_CAR_SERVICE_SDK_VERSIONS) {
    "The Application is required min-sdk version is ${SUPPORTED_CAR_SERVICE_SDK_VERSIONS.joinToString()}"
}
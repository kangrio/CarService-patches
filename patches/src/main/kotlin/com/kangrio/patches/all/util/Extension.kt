package com.kangrio.patches.all.util

import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.kangrio.patches.all.misc.Constant.MAX_SUPPORTED_APP_SDK_VERSION
import com.kangrio.patches.all.misc.Constant.SUPPORTED_CAR_SERVICE_SDK_VERSIONS

fun Instruction.replacementOrNull(mapping: Map<String, String>): String? =
    ((this as? ReferenceInstruction)?.reference as? StringReference)
        ?.string
        ?.let(mapping::get)

fun Int.requireAppMinSdk() = require(this <= MAX_SUPPORTED_APP_SDK_VERSION) {
    "The Application is required min-sdk version is $MAX_SUPPORTED_APP_SDK_VERSION or lower"
}

fun Int.requireCarServiceMinSdk() = require(this in SUPPORTED_CAR_SERVICE_SDK_VERSIONS) {
    "The CarService SDK is required version is ${SUPPORTED_CAR_SERVICE_SDK_VERSIONS.joinToString()}"
}
package com.kangrio.patches.all.misc.automotive

import app.morphe.patcher.patch.bytecodeContext
import app.morphe.patcher.patch.patch
import com.kangrio.patches.all.util.requireCarServiceMinSdk

fun mergeExtension(minSdk: Int) = patch {
    extendWith("extensions/sdk$minSdk.mpe")
}

fun addCarFrameworkSdkPatch(minSdkValue: Int) = patch {
    execute {
        minSdkValue.requireCarServiceMinSdk()
        val method = bytecodeContext.javaClass.methods.first { method ->
            method.name.startsWith("mergeExtension")
        }
        method.isAccessible = true
        method.invoke(bytecodeContext, mergeExtension(minSdkValue))
        println(method)
    }
}

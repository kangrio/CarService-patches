package com.kangrio.patches.all.misc.automotive

import app.morphe.patcher.patch.bytecodeContext
import app.morphe.patcher.patch.document
import app.morphe.patcher.patch.patch
import com.kangrio.patches.all.util.requireMinSdk

fun mergeExtension(minSdk: Int) = patch {
    extendWith("extensions/sdk$minSdk.mpe")
}

val addCarFrameworkSdkPatch = patch {
    execute {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement
            val usesSdk = manifest.getElementsByTagName("uses-sdk").item(0)
            val minSdkValue = usesSdk.attributes.getNamedItem("android:minSdkVersion").nodeValue.toInt()
            minSdkValue.requireMinSdk()

            val method = bytecodeContext.javaClass.methods.first { method ->
                method.name.startsWith("mergeExtension")
            }
            method.isAccessible = true
            method.invoke(bytecodeContext, mergeExtension(minSdkValue))
            println(method)
        }
    }
}

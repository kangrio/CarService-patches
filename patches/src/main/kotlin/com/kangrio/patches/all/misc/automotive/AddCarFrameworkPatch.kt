package com.kangrio.patches.all.misc.automotive

import app.morphe.patcher.patch.bytecodeContext
import app.morphe.patcher.patch.document
import app.morphe.patcher.patch.patch

fun mergeExtension(minSdk: Int) = patch {
    extendWith("extensions/sdk$minSdk.mpe")
}

val addCarFrameworkSdkPatch = patch {
    execute {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement
            val usesSdk = manifest.getElementsByTagName("uses-sdk").item(0)
            val minSdkValue = usesSdk.attributes.getNamedItem("android:minSdkVersion").nodeValue.toInt()
            require(minSdkValue in listOf(29, 32)) {
                "The Application is required min-sdk version is 29 or 32"
            }

            val method = bytecodeContext.javaClass.methods.first { method ->
                method.name.startsWith("mergeExtension")
            }
            method.isAccessible = true
            method.invoke(bytecodeContext, mergeExtension(minSdkValue))
            println(method)
        }
    }
}

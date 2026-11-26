package com.kangrio.patches.all.misc.automotive

import app.morphe.patcher.patch.document
import app.morphe.patcher.patch.patch
import com.kangrio.patches.all.util.requireMinSdk

val compatibilityCheck = patch {
    execute {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement
            val usesSdk = manifest.getElementsByTagName("uses-sdk").item(0)
            val minSdk = usesSdk.attributes.getNamedItem("android:minSdkVersion").nodeValue.toInt()
            minSdk.requireMinSdk()
        }
    }
}
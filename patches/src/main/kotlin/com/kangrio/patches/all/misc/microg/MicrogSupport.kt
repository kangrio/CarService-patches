package com.kangrio.patches.all.misc.microg

import app.morphe.patcher.PackageMetadata
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.document
import app.morphe.patcher.patch.packageMetadata
import app.morphe.patcher.patch.patch
import app.morphe.util.adoptChild
import app.morphe.util.findElementByAttributeValue
import com.kangrio.patches.all.misc.Constant.KNOW_CERTIFICATES
import org.w3c.dom.Element
import java.io.File
import java.util.Base64

@Suppress("unused")
val microGSupportPatch = patch(
//    name = "MicroG Support",
    description = "Make support MicroG of this project https://github.com/GStore0/GStore",
//    default = false,
) {
    dependsOn(
        patch {
            extendWith("extensions/shared.mpe")
        }
    )

    execute {
        val packageMetadata = packageMetadata
        val signature = KNOW_CERTIFICATES[packageMetadata.packageName] ?: getSignatureBase64(packageMetadata)
        var patchVersion = Int.MAX_VALUE

        try {
            val file = File("patch-version.txt")
            if (file.exists()) {
                val version = file.readText().trim()
                patchVersion = version.replace(Regex("[^0-9]"), "").toInt()
            }
        }catch (e: Throwable) {
            e.printStackTrace()
        }

        document("AndroidManifest.xml").use { document ->
            val childNodes = document.documentElement.childNodes
            childNodes.findElementByAttributeValue("android:name", "android.permission.QUERY_ALL_PACKAGES") ?: run {
                document.documentElement.adoptChild("uses-permission") {
                    setAttribute("android:name", "android.permission.QUERY_ALL_PACKAGES")
                }
            }

            val applicationNode =
                document
                    .getElementsByTagName("application")
                    .item(0) as Element

            applicationNode.setAttribute(
                "android:appComponentFactory",
                "com.kangrio.extension.SpoofAppComponentFactory"
            )

            applicationNode.adoptChild("meta-data") {
                setAttribute("android:name", "org.microg.gms.spoofed_certificates")
                setAttribute("android:value", signature)
            }
            applicationNode.adoptChild("meta-data") {
                setAttribute("android:name", "morphe_version")
                setAttribute("android:value", patchVersion.toString())
            }
        }
    }
}

private fun getSignatureBase64(packageMetadata: PackageMetadata): String {
    val cert = packageMetadata.signingCertificates.values.firstOrNull()?.firstOrNull()
        ?: throw PatchException("No certificate found")

    return Base64.getMimeEncoder()
        .encodeToString(cert.encoded)
}

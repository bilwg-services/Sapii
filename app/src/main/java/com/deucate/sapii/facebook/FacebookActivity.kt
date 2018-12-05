package com.deucate.sapii.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException

class FacebookActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "I found this amazing app //link to the app")

        val installed = checkAppInstall("com.facebook.katana")
        if (installed) {
            intent.setPackage("com.facebook.katana")
            startActivity(intent)
        } else {
            Toast.makeText(
                applicationContext,
                "Installed application first", Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun checkAppInstall(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: NameNotFoundException) {
        }

        return false
    }

}

package com.deucate.sapii.util

import android.app.AlertDialog
import android.content.Context

class Utils(val context: Context) {

    fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("OK") { _, _ -> }.show()
    }

}
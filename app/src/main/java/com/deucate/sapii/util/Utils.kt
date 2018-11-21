package com.deucate.sapii.util

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

class Utils(val context: Context) {

    fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("OK") { _, _ -> }.show()
    }

    fun showToastMessage(message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}
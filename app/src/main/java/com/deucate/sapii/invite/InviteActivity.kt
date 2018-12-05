package com.deucate.sapii.invite

import android.app.Activity
import android.os.Bundle
import android.content.Intent


class InviteActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Sapii")
        var sAux = "\nLet me recommend you this application\n\n"
        sAux += "https://play.google.com/store/apps/details?id=com.deucate.sapii \n\n"
        i.putExtra(Intent.EXTRA_TEXT, sAux)
        startActivity(Intent.createChooser(i, "choose one"))
    }
}

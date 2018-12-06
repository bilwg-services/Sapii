package com.deucate.sapii.invite


import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deucate.sapii.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_invite.view.*

class InviteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_invite, container, false)

        rootView.inviteID.text = SpannableStringBuilder(FirebaseAuth.getInstance().uid)

        rootView.inviteButton.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Sapii")
            var sAux = "\nLet me recommend you this application\n\n"
            sAux += "https://play.google.com/store/apps/details?id=com.deucate.sapii \n\n"
            i.putExtra(Intent.EXTRA_TEXT, sAux)
            startActivity(Intent.createChooser(i, "choose one"))
        }
        return rootView
    }

}

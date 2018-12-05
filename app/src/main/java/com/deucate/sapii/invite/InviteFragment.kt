package com.deucate.sapii.invite


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deucate.sapii.R

class InviteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        startActivity(Intent(activity!!, InviteActivity::class.java))
        return inflater.inflate(R.layout.fragment_invite, container, false)
    }

}

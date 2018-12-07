package com.deucate.sapii.settings


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.deucate.sapii.util.Constants
import com.deucate.sapii.R
import com.deucate.sapii.login.LoginActivity
import com.deucate.sapii.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val constants = Constants()
    private lateinit var utils: Utils
    private val user = MutableLiveData<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        utils = Utils(activity!!)
        Picasso.get().load(auth.currentUser!!.photoUrl).fit().into(rootView.accountProfilePicture)

        db.collection(constants.Path_Users).document(auth.uid!!).get().addOnCompleteListener {
            if (it.isSuccessful) {
                user.value = it.result!!.toObject(User::class.java)
            } else {
                utils.showToastMessage(it.exception!!.localizedMessage)
            }
        }

        rootView.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity!!.finish()
        }

        user.observe(this, Observer { user ->
            user?.let {
                rootView.accountName.text = it.Name
                rootView.accountContact.text = it.Contact
                rootView.settingsPoint.text = it.Points.toString()
                rootView.settingsEarnings.text = it.LifeTimeEarning.toString()
            }
        })

        return rootView
    }


}

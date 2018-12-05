package com.deucate.sapii.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deucate.sapii.Constants
import com.deucate.sapii.MainActivity
import com.deucate.sapii.R
import com.deucate.sapii.util.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.dialoge_edittext.view.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private val auth = FirebaseAuth.getInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    private val utils = Utils(this)
    private val constants = Constants()

    private val signIn: Int = 69

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        if (auth.currentUser != null) {
            startHomeActivity()
        }
        setContentView(R.layout.activity_login)

        findViewById<SignInButton>(R.id.signInButton).setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, signIn)
        }

        googleSignInClient = GoogleSignIn.getClient(
                this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        viewModel.isUserFirstTimer.observe(this, Observer {
            if (!it!!) {
                if (viewModel.userName.value != null) {
                    utils.showToastMessage(viewModel.userName.value!!)
                }
                startHomeActivity()
            } else {
                addData()

                val dialogView = layoutInflater.inflate(R.layout.dialoge_edittext, null)
                AlertDialog.Builder(this).setTitle("referral Code").setMessage("Enter referral code here.")
                        .setView(dialogView).setPositiveButton("Done") { _, _ ->
                            val referralCode = dialogView.edit1.text.toString()
                            if (TextUtils.isEmpty(referralCode)) {
                                startHomeActivity()
                            } else {
                                viewModel.addReferralCode(referralCode)
                            }
                        }.show()
            }
        })

        viewModel.error.observe(this, Observer {
            if (it == null) {
                startHomeActivity()
            } else {
                utils.showAlertDialog("Error", it)
            }
        })

        viewModel.userName.observe(this, Observer {
            if (it != null) {
                utils.showToastMessage(it)
            }
        })

    }

    private fun addData() {
        val currentUser = auth.currentUser ?: return

        var contact = currentUser.email
        if (contact == null || contact == "")
            contact = currentUser.phoneNumber

        val data = HashMap<String, Any?>()
        data[constants.user_name] = currentUser.displayName
        data[constants.contact] = contact
        data[constants.points] = 0

        viewModel.addNewData(data,auth.uid!!)
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            signIn -> {
                try {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
                    signInToFirebase(account!!)
                } catch (e: ApiException) {
                    utils.showAlertDialog("Error", e.localizedMessage)
                } catch (e: NullPointerException) {
                    utils.showAlertDialog("Error", e.localizedMessage)
                }
            }
        }
    }

    private fun signInToFirebase(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        viewModel.checkUserFirstTimer(task.result!!.user.uid)
                    } else {
                        utils.showAlertDialog("Error", task.exception!!.localizedMessage)
                    }
                }
    }


}

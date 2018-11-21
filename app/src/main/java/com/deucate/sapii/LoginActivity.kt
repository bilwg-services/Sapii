package com.deucate.sapii

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deucate.sapii.util.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_login.view.*
import org.koin.android.architecture.ext.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginViewModel>()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    private val utils = Utils(this)

    private val signIn: Int = 69

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (auth.currentUser != null) {
            startHomeActivity()
        }
        setContentView(R.layout.activity_login)
        viewModel.auth.value = auth
        val rootView = layoutInflater.inflate(R.layout.activity_login, null)

        googleSignInClient = GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        rootView.googleSignInButton.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, signIn)
        }

    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
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
                    startHomeActivity()
                } else {
                    utils.showAlertDialog("Error", task.exception!!.localizedMessage)
                }
            }
    }


}

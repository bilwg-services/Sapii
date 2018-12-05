package com.deucate.sapii.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deucate.sapii.Constants
import com.deucate.sapii.util.Utils
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

class LoginViewModel : ViewModel() {

    val isUserFirstTimer = MutableLiveData<Boolean?>()
    val userName = MutableLiveData<String?>()
    val error = MutableLiveData<String?>()

    private val constants = Constants()

    private val db = FirebaseFirestore.getInstance()

    fun checkUserFirstTimer(uid: String) {
        db.collection("Users").document(uid).get().addOnCompleteListener {
            if (it.isSuccessful) {
                userName.value = it.result!!.getString("Name")
                isUserFirstTimer.value = userName.value == null
            } else {
                isUserFirstTimer.value = true
            }
        }
    }

    fun addReferralCode(referralCode: String) {
        val notExistsError = "Not found referral code."

        db.collection(constants.Path_Users).document(referralCode).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val doc = it.result!!
                val updatedPoint = try {
                    doc.getLong(constants.points)!!.toInt() + constants.Referral_Point
                } catch (e: NullPointerException) {
                    constants.Referral_Point
                }
                addThePoints(doc.id, updatedPoint)
            } else {
                error.value = notExistsError
            }
        }
    }

    private fun addThePoints(uid: String, points: Int) {
        val data = HashMap<String, Any>()
        data[constants.points] = points

        db.collection(constants.Path_Users).document(uid).update(data).addOnCompleteListener {
            if (it.isSuccessful) {
                error.value = null
            } else {
                error.value = "Error while update points"
            }
        }

    }

    fun addNewData(data: java.util.HashMap<String, Any?>) {
        db.collection(constants.Path_Users).add(data).addOnCompleteListener {
            if (!it.isSuccessful) {
                error.value = it.exception!!.localizedMessage
            }
        }
    }

}
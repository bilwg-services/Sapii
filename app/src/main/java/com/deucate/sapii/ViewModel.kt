package com.deucate.sapii

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewModel : ViewModel() {

    val points = MutableLiveData<Long>()

    private val constants = Constants()

    init {
        getPoints()
    }

    fun updatePoints(point: Long) {
        FirebaseFirestore.getInstance().collection(constants.Path_Users).document(FirebaseAuth.getInstance().uid!!).update(constants.points, point)
    }

    fun getPoints() {
        FirebaseFirestore.getInstance().collection(constants.Path_Users).document(FirebaseAuth.getInstance().uid!!).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val point = it.result!!.getLong(constants.points)!!.toLong()
                if (points.value != point)
                    points.value = point
            }
        }
    }


}
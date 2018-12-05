package com.deucate.sapii

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    val points = MutableLiveData<Int>()

    private val constants = Constants()

    init {
        getPoints()
    }

     fun getPoints() {
        FirebaseFirestore.getInstance().collection(constants.Path_Users).document(FirebaseAuth.getInstance().uid!!).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val point = it.result!!.getLong(constants.points)!!.toInt()
                if (points.value != point)
                    points.value = point
            }
        }
    }


}
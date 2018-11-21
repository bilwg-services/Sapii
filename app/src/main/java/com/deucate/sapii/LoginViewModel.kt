package com.deucate.sapii

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    val auth = MutableLiveData<FirebaseAuth>()

}
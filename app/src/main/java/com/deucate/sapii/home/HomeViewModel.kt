package com.deucate.sapii.home

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val error = MutableLiveData<String?>()

    val modules = MutableLiveData<ArrayList<Module>>()

    val activityToStart = MutableLiveData<Intent>()

    init {
        modules.value = ArrayList()
    }
}
package com.deucate.sapii.scatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deucate.sapii.R
import com.deucate.sapii.util.Utils

class ScratchActivity : AppCompatActivity() {

    val utils = Utils(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)

    }

}

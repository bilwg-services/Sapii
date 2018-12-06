package com.deucate.sapii.spinner

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.deucate.sapii.Constants
import com.deucate.sapii.R
import com.deucate.sapii.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_spin.*
import rubikstudio.library.LuckyWheelView
import rubikstudio.library.model.LuckyItem
import java.util.*


class SpinActivity : AppCompatActivity() {

    private val colors = arrayListOf("#FFE0B2", "#FFCC80", "#FFB74D", "#FFA726", "#FF9800", "#FB8C00", "#F57C00", "#EF6C00", "#E65100")
    private val drawables = arrayListOf(R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4, R.drawable.test4, R.drawable.test6, R.drawable.test7, R.drawable.test8, R.drawable.test9)

    private lateinit var viewModel: com.deucate.sapii.ViewModel

    private val constants = Constants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

        val luckyWheelView = findViewById<View>(R.id.luckyWheel) as LuckyWheelView
        val data = ArrayList<LuckyItem>()
        for (i in 0..8) {
            val luckyItem1 = LuckyItem()
            luckyItem1.text = "${i + 1}0"
            luckyItem1.icon = drawables[i]
            luckyItem1.color = Color.parseColor(colors[i])
            data.add(luckyItem1)
        }
        luckyWheelView.setData(data)
        luckyWheelView.setRound(4)
        luckyWheelView.setLuckyRoundItemSelectedListener {
            val point = it * 10
            viewModel.points.value = viewModel.points.value!!.toLong() + point
        }

        viewModel.points.observe(this, androidx.lifecycle.Observer {
            FirebaseFirestore.getInstance().collection(constants.Path_Users).document(FirebaseAuth.getInstance().uid!!).update(constants.points, it)
        })

        spinStartBtn.setOnClickListener {
            val randomNumber = Random().nextInt(8)
            luckyWheelView.startLuckyWheelWithTargetIndex(randomNumber + 1)
        }
    }

    override fun finish() {
        val intent = intent
        intent.putExtra(constants.points, viewModel.points.value!!)
        setResult(Activity.RESULT_OK, intent)
        super.finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

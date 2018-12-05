package com.deucate.sapii.spinner

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.deucate.sapii.Constants
import com.deucate.sapii.MainActivity
import com.deucate.sapii.MainViewModel
import com.deucate.sapii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_spin.*
import org.koin.android.architecture.ext.viewModel
import rubikstudio.library.LuckyWheelView
import rubikstudio.library.model.LuckyItem
import java.util.*


class SpinActivity : AppCompatActivity() {

    private val colors = ArrayList<String>()
    private val drawables = ArrayList<Int>()

    private lateinit var viewModel: MainViewModel

    init {
        colors.add("#FFE0B2")
        colors.add("#FFCC80")
        colors.add("#FFB74D")
        colors.add("#FFA726")
        colors.add("#FF9800")
        colors.add("#FB8C00")
        colors.add("#F57C00")
        colors.add("#EF6C00")
        colors.add("#E65100")

        drawables.add(R.drawable.test1)
        drawables.add(R.drawable.test2)
        drawables.add(R.drawable.test3)
        drawables.add(R.drawable.test4)
        drawables.add(R.drawable.test5)
        drawables.add(R.drawable.test6)
        drawables.add(R.drawable.test7)
        drawables.add(R.drawable.test8)
        drawables.add(R.drawable.test9)
    }

    private val constants = Constants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

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
            viewModel.points.value = viewModel.points.value!!.toInt() + point
        }

        viewModel.points.observe(this, androidx.lifecycle.Observer {
            FirebaseFirestore.getInstance().collection(constants.Path_Users).document(FirebaseAuth.getInstance().uid!!).update(constants.points, it)
        })

        spinStartBtn.setOnClickListener {
            val randomNumber = Random().nextInt(8)
            luckyWheelView.startLuckyWheelWithTargetIndex(randomNumber + 1)
        }
    }
}

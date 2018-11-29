package com.deucate.sapii.spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deucate.sapii.R
import rubikstudio.library.LuckyWheelView
import rubikstudio.library.model.LuckyItem


class SpinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        val luckyWheelView = findViewById<View>(R.id.luckyWheel) as LuckyWheelView
        val data = ArrayList<LuckyItem>()
        for (i in 0..11) {
            val luckyItem1 = LuckyItem()
            luckyItem1.text = "$i"
            luckyItem1.icon = R.drawable.test1
            luckyItem1.color = -0xc20
            data.add(luckyItem1)
        }
        luckyWheelView.setData(data)
        luckyWheelView.setRound(3)
        luckyWheelView.startLuckyWheelWithTargetIndex(0)
        luckyWheelView.setLuckyRoundItemSelectedListener {
            // do something with index
        }
    }
}

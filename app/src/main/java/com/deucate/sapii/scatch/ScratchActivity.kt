package com.deucate.sapii.scatch

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cooltechworks.views.ScratchTextView
import com.deucate.sapii.Constants
import com.deucate.sapii.R
import com.deucate.sapii.util.Utils
import kotlinx.android.synthetic.main.activity_scratch.*
import kotlin.random.Random


class ScratchActivity : AppCompatActivity() {

    private val constants = Constants()
    private var points: Long = 0
    private val scratchPoint = Random.nextLong(100)
    private var isReveled = false
    private val utils = Utils(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)

        scratchCardView.text = "$scratchPoint"
        points = intent.getLongExtra(constants.points, 0)

        scratchCardView.setRevealListener(object : ScratchTextView.IRevealListener {
            override fun onRevealed(p0: ScratchTextView?) {}
            override fun onRevealPercentChangedListener(p0: ScratchTextView?, p1: Float) {
                isReveled = true
                utils.showAlertDialogWithCallBack("Congratulations!!", "you earned $scratchPoint.", object : Utils.OnAlertCall {
                    override fun onClickPositive() {
                        finish()
                    }
                })
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun finish() {
        var result = points
        val intent = intent

        if (isReveled) {
            result += scratchPoint
            intent.putExtra(constants.points, result)
            setResult(Activity.RESULT_OK, intent)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        super.finish()
    }

}

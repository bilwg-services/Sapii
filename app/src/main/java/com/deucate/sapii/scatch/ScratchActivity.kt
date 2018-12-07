package com.deucate.sapii.scatch

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cooltechworks.views.ScratchTextView
import com.deucate.sapii.util.Constants
import com.deucate.sapii.R
import com.deucate.sapii.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_scratch.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class ScratchActivity : AppCompatActivity() {

    private val constants = Constants()
    private var points: Long = 0
    private val scratchPoint = Random.nextLong(100)
    private var isReveled = false
    private val utils = Utils(this)

    private val remainTime = MutableLiveData<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)

        scratchCardView.text = "$scratchPoint"
        points = intent.getLongExtra(constants.points, 0)
        timeRemainProcess.maxProgress = 30.toDouble()
        timeRemainProcess.setCurrentProgress(30.toDouble())

        remainTime.observe(this, Observer { time ->
            time?.let {
                timeRemainProcess.setCurrentProgress(30 - it.toDouble())
            }
        })

        FirebaseFirestore.getInstance().collection(constants.Path_Users)
            .document(FirebaseAuth.getInstance().uid!!).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val lastTime = it.result!!.getTimestamp(constants.lastTime)
                    if (lastTime != null) {
                        val remainTimeInMillis =
                            Calendar.getInstance().timeInMillis - lastTime.toDate().time
                        if (remainTimeInMillis < TimeUnit.MINUTES.toMillis(30)) {
                            remainTime.value = TimeUnit.MILLISECONDS.toMinutes(remainTimeInMillis)
                            reduceMinutes()
                        } else {
                            remainTime.value = 30
                        }

                    }
                } else {
                    utils.showAlertDialog("Error", it.exception!!.localizedMessage)
                }
            }

        scratchCardView.setRevealListener(object : ScratchTextView.IRevealListener {
            override fun onRevealed(p0: ScratchTextView?) {}
            override fun onRevealPercentChangedListener(p0: ScratchTextView?, p1: Float) {
                isReveled = true
                utils.showAlertDialogWithCallBack(
                    "Congratulations!!",
                    "you earned $scratchPoint.",
                    object : Utils.OnAlertCall {
                        override fun onClickPositive() {
                            finish()
                        }
                    })
            }
        })

    }

    private fun reduceMinutes() {
        val handler = Handler()
        handler.postDelayed({
            remainTime.value = remainTime.value!! - 1
        }, 60000)
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

package com.adedom.theegggame.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.adedom.theegggame.R

class FightGameDialog : DialogFragment() { // 5/8/62

    private lateinit var mTvScore: TextView
    private lateinit var mBtnOne: Button
    private lateinit var mBtnTwo: Button
    private lateinit var mBtnThree: Button
    private lateinit var mBtnFour: Button
    private lateinit var mBtnFive: Button
    private lateinit var mBtnSix: Button
    private lateinit var mBtnSeven: Button
    private lateinit var mBtnEight: Button
    private lateinit var mBtnNine: Button
    private lateinit var mBtnGiveUp: Button
    private val mHandlerRndButton = Handler()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_fight_game, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setTitle(R.string.fight_game)

        // todo fighting game

        bindWidgets(view)
        setEvents()
        mRunnableRndButton.run()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mTvScore = view.findViewById(R.id.mTvScore) as TextView
        mBtnOne = view.findViewById(R.id.mBtnOne) as Button
        mBtnTwo = view.findViewById(R.id.mBtnTwo) as Button
        mBtnThree = view.findViewById(R.id.mBtnThree) as Button
        mBtnFour = view.findViewById(R.id.mBtnFour) as Button
        mBtnFive = view.findViewById(R.id.mBtnFive) as Button
        mBtnSix = view.findViewById(R.id.mBtnSix) as Button
        mBtnSeven = view.findViewById(R.id.mBtnSeven) as Button
        mBtnEight = view.findViewById(R.id.mBtnEight) as Button
        mBtnNine = view.findViewById(R.id.mBtnNine) as Button
        mBtnGiveUp = view.findViewById(R.id.mBtnGiveUp) as Button
    }

    private fun setEvents() {
        mBtnOne.setOnClickListener { view -> setScore(view) }
        mBtnTwo.setOnClickListener { view -> setScore(view) }
        mBtnThree.setOnClickListener { view -> setScore(view) }
        mBtnFour.setOnClickListener { view -> setScore(view) }
        mBtnFive.setOnClickListener { view -> setScore(view) }
        mBtnSix.setOnClickListener { view -> setScore(view) }
        mBtnSeven.setOnClickListener { view -> setScore(view) }
        mBtnEight.setOnClickListener { view -> setScore(view) }
        mBtnNine.setOnClickListener { view -> setScore(view) }
        mBtnGiveUp.setOnClickListener {
            mHandlerRndButton.removeCallbacks(mRunnableRndButton)
            dialog!!.dismiss()
        }
    }

    private fun setScore(view: View) {
        //todo first win only

        val s = view.tag.toString()
        if (s == "1") {
            val num = mTvScore.text.toString().toInt() + 1
            mTvScore.text = num.toString()
        } else if (s == "0") {
            var num = mTvScore.text.toString().toInt()
            if (num <= 0) {
                mTvScore.text = "0"
            } else {
                num -= 1
                mTvScore.text = num.toString()
            }
        }
    }

    private val mRunnableRndButton = object : Runnable {
        override fun run() {
            mHandlerRndButton.postDelayed(this, 600)
            rndFightGame()
        }
    }

    private fun rndFightGame() {
        setButtonBackground(mBtnOne, 0)
        setButtonBackground(mBtnTwo, 0)
        setButtonBackground(mBtnThree, 0)
        setButtonBackground(mBtnFour, 0)
        setButtonBackground(mBtnFive, 0)
        setButtonBackground(mBtnSix, 0)
        setButtonBackground(mBtnSeven, 0)
        setButtonBackground(mBtnEight, 0)
        setButtonBackground(mBtnNine, 0)

        var num = 0
        while (num == 0) {
            num = (Math.random() * 10).toInt()
        }

        when (num) {
            1 -> setButtonBackground(mBtnOne, 1)
            2 -> setButtonBackground(mBtnTwo, 1)
            3 -> setButtonBackground(mBtnThree, 1)
            4 -> setButtonBackground(mBtnFour, 1)
            5 -> setButtonBackground(mBtnFive, 1)
            6 -> setButtonBackground(mBtnSix, 1)
            7 -> setButtonBackground(mBtnSeven, 1)
            8 -> setButtonBackground(mBtnEight, 1)
            9 -> setButtonBackground(mBtnNine, 1)
        }
    }

    private fun setButtonBackground(button: Button, image: Int) {
        val drawable: Int
        if (image == 0) {
            drawable = R.drawable.shape_btn_fight_gray
            button.tag = 0
        } else {
            drawable = R.drawable.shape_btn_fight_game
            button.tag = 1
        }
        button.background = ContextCompat.getDrawable(context!!, drawable)
    }
}

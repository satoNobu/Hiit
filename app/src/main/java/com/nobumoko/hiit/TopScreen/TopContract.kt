package com.nobumoko.hiit.TopScreen

import android.content.Context
import com.nobumoko.hiit.Model.Constants

interface TopContract {
    interface View {
        fun workEnd()
        fun setTxtReady(countTimer: Long)
        fun readyEnd()
        fun repeatStart()
        fun setCountNow(setCount: Int)
        fun showCountDownTimer(progressValue: String)
        fun showToast(message: String)
        fun setMaxValueOfProgressBar(maxValue: Int)
        fun setProgressValueOfProgressBar(progressValue: Int)
        fun setSettingData(workTime: Int, restTime: Int, setCount: Int)
        fun setBackColor(state: Constants.HiitState)
    }

    interface Presenter {
        fun init(context: Context)
        fun startWorkUpBtn(workTime: Int, restTime: Int, setCount: Int)
        fun stopTts()
    }
}

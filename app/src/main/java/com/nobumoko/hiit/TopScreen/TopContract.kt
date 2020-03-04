package com.nobumoko.hiit.TopScreen

import android.content.Context

interface TopContract {
    interface View {
        fun showCountDownTimer(progressValue: String)
        fun showToast(message: String)
        fun setMaxValueOfProgressBar(maxValue: Int)
        fun setProgressValueOfProgressBar(progressValue: Int)
        fun setSettingData(workTime: Int, restTime: Int, setCount: Int)
    }

    interface Presenter {
        fun init(context: Context)
        fun startWorkUpBtn(workTime: Int, restTime: Int, setCount: Int)
        fun stopTts()
    }
}

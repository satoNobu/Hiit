package com.nobumoko.hiit.TopScreen

interface TopContract {
    interface View {
        fun showCountDownTimer(progressValue: String)
        fun showToast(message: String)
        fun setMaxValueOfProgressBar(maxValue: Int)
        fun setProgressValueOfProgressBar(progressValue: Int)
    }

    interface Presenter {
        fun startWorkUpBtn(workTime: Int, restTime: Int, setCount: Int)
        fun stopTts()
    }
}

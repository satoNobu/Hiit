package com.nobumoko.hiit.SettingScreen

interface SettingContract {
    interface View {
        fun updateSettingData(workTime: Int, restTime: Int, setCount: Int, useTTS: Boolean)
    }

    interface Presenter {
        fun getSettingData()
        fun setUseTTS(boolean: Boolean)
        fun getInitTTS(): Boolean
    }
}
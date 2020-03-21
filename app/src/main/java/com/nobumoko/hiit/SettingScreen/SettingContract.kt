package com.nobumoko.hiit.SettingScreen

import com.nobumoko.hiit.Model.Constants

interface SettingContract {
    interface View {
        fun updateSettingData(workTime: Int, restTime: Int, setCount: Int, useTTS: Boolean)
    }

    interface Presenter {
        fun saveSettingData(value: Int, dataType: Constants.SettingData)
        fun getSettingData()
        fun setUseTTS(boolean: Boolean)
        fun getInitTTS(): Boolean
    }
}
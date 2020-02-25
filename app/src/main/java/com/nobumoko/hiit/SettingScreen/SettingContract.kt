package com.nobumoko.hiit.SettingScreen

interface SettingContract {
    interface View {
        fun updateSettingData(workTime:Int, restTime:Int, setCount:Int)
    }
    interface Presenter {
        fun getSettingData()
    }
}
package com.nobumoko.hiit.SettingScreen

import com.nobumoko.hiit.Model.Constants
import com.nobumoko.hiit.Model.SettingPreferenceRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val pre: SettingPreferenceRepository
) : SettingContract.Presenter {

    override fun saveSettingData(value: Int, dataType: Constants.SettingData) {
        when (dataType) {
            Constants.SettingData.WORK_TIME -> pre.saveWorkTime(value)
            Constants.SettingData.REST_TIME -> pre.saveRestTime(value)
            Constants.SettingData.SET_COUNT -> pre.saveSetCount(value)
            else -> return
        }
    }

    override fun getSettingData() {
        view.updateSettingData(
            pre.getWorkTime(),
            pre.getRestTime(),
            pre.getSetCount(),
            pre.getUserTTS()
        )
    }

    override fun getInitTTS(): Boolean {
        return pre.getInitTTS()
    }

    override fun setUseTTS(boolean: Boolean) {
        pre.saveUseTTS(boolean)
    }
}
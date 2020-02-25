package com.nobumoko.hiit.PickerDialog

import com.nobumoko.hiit.Model.Constants
import com.nobumoko.hiit.Model.SettingPreferenceRepository

class PickerPresenter(
    private val view: PickerContract.View,
    private val pre: SettingPreferenceRepository
) : PickerContract.Presenter {

    override fun clickOkBtn(value: Int, dataType: Constants.SettingData) {
        when(dataType) {
            Constants.SettingData.WORK_TIME -> pre.saveWorkTime(value)
            Constants.SettingData.REST_TIME -> pre.saveRestTime(value)
            Constants.SettingData.SET_COUNT -> pre.saveSetCount(value)
        }
        view.resultOfOkBtn()
    }
}


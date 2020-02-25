package com.nobumoko.hiit.SettingScreen

import com.nobumoko.hiit.Model.SettingPreferenceRepository

public class SettingPresenter(
    private val view: SettingContract.View,
    private val pre: SettingPreferenceRepository) : SettingContract.Presenter {

    override fun getSettingData() {
        view.updateSettingData(pre.getWorkTime(), pre.getRestTime(), pre.getSetCount())
    }
}
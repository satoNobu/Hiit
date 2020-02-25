package com.nobumoko.hiit.Model

import android.content.Context
import android.preference.PreferenceManager

class SettingPreferenceRepository(context: Context) : SettingRepository {
    private val pre = PreferenceManager.getDefaultSharedPreferences(context)
    override fun saveWorkTime(time: Int) {
        pre.edit()
            .putInt(Constants.SettingData.WORK_TIME.toString(), time)
            .apply()
    }

    override fun getWorkTime(): Int {
        return pre.getInt(Constants.SettingData.WORK_TIME.toString(), 0)
    }
    override fun saveRestTime(time: Int) {
        pre.edit()
            .putInt(Constants.SettingData.REST_TIME.toString(), time)
            .apply()
    }

    override fun getRestTime(): Int {
        return pre.getInt(Constants.SettingData.REST_TIME.toString(), 0)
    }
    override fun saveSetCount(count: Int) {
        pre.edit()
            .putInt(Constants.SettingData.SET_COUNT.toString(), count)
            .apply()
    }

    override fun getSetCount(): Int {
        return pre.getInt(Constants.SettingData.SET_COUNT.toString(), 0)
    }
}
package com.nobumoko.hiit.Model

interface SettingRepository {
    fun saveWorkTime(time: Int)
    fun saveRestTime(time: Int)
    fun saveSetCount(count: Int)
    fun saveUseTTS(use: Boolean)
    fun saveInitTTS(isInit: Boolean)
    fun getWorkTime(): Int
    fun getRestTime(): Int
    fun getSetCount(): Int
    fun getUserTTS(): Boolean
    fun getInitTTS(): Boolean
}
package com.nobumoko.hiit.Model

import java.lang.Exception

interface SettingRepository {
    fun saveWorkTime(time: Int)
    fun saveRestTime(time: Int)
    fun saveSetCount(count: Int)
    fun getWorkTime(): Int
    fun getRestTime(): Int
    fun getSetCount(): Int
}
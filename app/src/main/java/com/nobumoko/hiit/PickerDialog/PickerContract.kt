package com.nobumoko.hiit.PickerDialog

import com.nobumoko.hiit.Model.Constants

interface PickerContract {

    interface CallBack {
        fun pickerDialogResult(value: Int, dataType: Constants.SettingData)
    }
}
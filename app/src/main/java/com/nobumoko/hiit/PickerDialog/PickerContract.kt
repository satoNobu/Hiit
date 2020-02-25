package com.nobumoko.hiit.PickerDialog

import com.nobumoko.hiit.Model.Constants

interface PickerContract {
    interface View {
        fun resultOfOkBtn()
    }

    interface Presenter {
        fun clickOkBtn(value: Int, dataType: Constants.SettingData)
    }

    interface CallBack {
        fun pickerDialogResult()
    }
}
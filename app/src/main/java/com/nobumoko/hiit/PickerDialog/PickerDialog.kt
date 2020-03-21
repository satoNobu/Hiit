package com.nobumoko.hiit.PickerDialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.nobumoko.hiit.Model.Constants
import com.nobumoko.hiit.R

class PickerDialog(
    private val dataType: Constants.SettingData,
    private val titleStr: String,
    private val maxValue: Int,
    private val minValue: Int,
    private val step: Int
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(titleStr)
            val inflater = requireActivity().layoutInflater;
            val view: View = inflater.inflate(R.layout.fragment_picker, null)
            val np = view.findViewById(R.id.numberPicker) as NumberPicker
            setNumberPickerValues(np)
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(getString(R.string.ok_btn),
                    DialogInterface.OnClickListener { dialog, id ->
                        if (activity is PickerContract.CallBack) {
                            val callback: PickerContract.CallBack = activity as PickerContract.CallBack
                            callback.pickerDialogResult(getNumberPickerValue(np.value), dataType = dataType)
                        }
                    })
                .setNegativeButton(getString(R.string.cancel_btn),
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getNumberPickerValue(value: Int): Int {
        return value * step + minValue
    }
    private fun setNumberPickerValues(np: NumberPicker) {
        np.maxValue = (maxValue - minValue) / step
        np.minValue = 0
        var strArray = ""
        var i = minValue
        while (i <= maxValue) {
            strArray += i
            if (i != maxValue) {
                strArray += ","
            }
            i += step
        }
        np.displayedValues = strArray.split(",").toTypedArray()
    }

}
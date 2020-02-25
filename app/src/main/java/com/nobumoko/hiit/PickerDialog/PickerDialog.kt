package com.nobumoko.hiit.PickerDialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.nobumoko.hiit.Model.Constants
import com.nobumoko.hiit.Model.SettingPreferenceRepository
import com.nobumoko.hiit.R

class PickerDialog(
    private val dataType: Constants.SettingData,
    private val titleStr: String,
    private val maxValue: Int,
    private val minValue: Int
) : DialogFragment(), PickerContract.View {

    val presenter by lazy {
        PickerPresenter(this, SettingPreferenceRepository(context!!))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(titleStr)
            val inflater = requireActivity().layoutInflater;
            val view: View = inflater.inflate(R.layout.fragment_picker, null)
            val np = view.findViewById(R.id.numberPicker) as NumberPicker
            np.maxValue = maxValue
            np.minValue = minValue
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(getString(R.string.ok_btn),
                    DialogInterface.OnClickListener { dialog, id ->
                        presenter.clickOkBtn(value = np.value, dataType = dataType)
                    })
                .setNegativeButton(getString(R.string.cancel_btn),
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun resultOfOkBtn() {
        if (activity is PickerContract.CallBack) {
            val listener: PickerContract.CallBack = activity as PickerContract.CallBack
            listener.pickerDialogResult()
        }
    }
}
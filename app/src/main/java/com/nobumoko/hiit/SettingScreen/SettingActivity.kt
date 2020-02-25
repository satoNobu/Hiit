package com.nobumoko.hiit.SettingScreen

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nobumoko.hiit.Model.Constants
import com.nobumoko.hiit.Model.SettingPreferenceRepository
import com.nobumoko.hiit.PickerDialog.PickerContract
import com.nobumoko.hiit.PickerDialog.PickerDialog
import com.nobumoko.hiit.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), PickerContract.CallBack, SettingContract.View {

    val presenter by lazy {
        SettingPresenter(this, SettingPreferenceRepository(applicationContext))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        workTimeEdit.setOnClickListener {
            showDialog(Constants.SettingData.WORK_TIME, getString(R.string.dialog_title_work_time), 120, 30)
        }
        restTimeEdit.setOnClickListener {
            showDialog(Constants.SettingData.REST_TIME, getString(R.string.dialog_title_rest_time), 60, 5)
        }
        setCntEdit.setOnClickListener {
            showDialog(Constants.SettingData.SET_COUNT, getString(R.string.dialog_title_set_count), 10, 1)
        }
    }

    override fun pickerDialogResult() {
        presenter.getSettingData()
    }

    override fun updateSettingData(workTime: Int, restTime: Int, setCount: Int) {
        workTimeEdit.text = workTime.toString()
        restTimeEdit.text = restTime.toString()
        setCntEdit.text = setCount.toString()
    }

    override fun onResume() {
        super.onResume()
        presenter.getSettingData()
    }

    private fun showDialog(dataType: Constants.SettingData, titleStr: String, maxValue: Int, minValue: Int) {
        // Dialogの表示
        val dialog = PickerDialog(dataType, titleStr, maxValue, minValue)
        dialog.show(supportFragmentManager, "setting_dialog")
    }
}
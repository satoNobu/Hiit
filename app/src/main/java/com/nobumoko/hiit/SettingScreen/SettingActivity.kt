package com.nobumoko.hiit.SettingScreen

import android.os.Bundle
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
            val dialog = PickerDialog(
                Constants.SettingData.WORK_TIME,
                getString(R.string.dialog_title_work_time),
                120,
                30,
                10)
            dialog.show(supportFragmentManager, "setting_dialog")
        }
        restTimeEdit.setOnClickListener {
            val dialog = PickerDialog(
                Constants.SettingData.REST_TIME,
                getString(R.string.dialog_title_rest_time),
                60,
                5,
                5)
            dialog.show(supportFragmentManager, "setting_dialog")
        }
        setCntEdit.setOnClickListener {
            val dialog = PickerDialog(
                Constants.SettingData.SET_COUNT,
                getString(R.string.dialog_title_set_count),
                10,
                1,
                1)
            dialog.show(supportFragmentManager, "setting_dialog")
        }
        if (presenter.getInitTTS()) {
            switchUseTTS.isEnabled = true
            switchUseTTS.setOnCheckedChangeListener { _, isChecked ->
                presenter.setUseTTS(isChecked)
            }
        } else {
            switchUseTTS.isEnabled = false
        }
    }

    override fun pickerDialogResult(value: Int, dataType: Constants.SettingData) {
        presenter.saveSettingData(value, dataType)
        presenter.getSettingData()
    }

    override fun updateSettingData(workTime: Int, restTime: Int, setCount: Int, useTTS: Boolean) {
        workTimeEdit.text = workTime.toString()
        restTimeEdit.text = restTime.toString()
        setCntEdit.text = setCount.toString()
        switchUseTTS.isChecked = useTTS
    }

    override fun onResume() {
        super.onResume()
        presenter.getSettingData()
    }
}
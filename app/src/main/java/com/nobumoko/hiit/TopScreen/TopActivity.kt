package com.nobumoko.hiit.TopScreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.nobumoko.hiit.Model.SettingPreferenceRepository
import com.nobumoko.hiit.Model.TextToSpeech
import com.nobumoko.hiit.R
import com.nobumoko.hiit.SettingScreen.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*


class TopActivity : AppCompatActivity(), TopContract.View {

    val presenter by lazy {
        TopPresenter(
            this,
            TextToSpeech(SettingPreferenceRepository(applicationContext)),
            SettingPreferenceRepository(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        presenter.init(applicationContext)
    }

    override fun showCountDownTimer(progressValue: String) {
        this.txtProgress.text = progressValue
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun setMaxValueOfProgressBar(maxValue: Int) {
        progressBar.max = maxValue
    }

    override fun setProgressValueOfProgressBar(progressValue: Int) {
        progressBar.progress = progressValue
    }

    override fun setSettingData(workTime: Int, restTime: Int, setCount: Int) {
        this.workTime.text = getString(R.string.workTime, workTime)
        this.restTime.text = getString(R.string.restTime, restTime)
        this.setCount.text = getString(R.string.setCount, setCount)
        this.startBtn.setOnClickListener {
            presenter.startWorkUpBtn(workTime = workTime, restTime = restTime, setCount = setCount)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopTts()
    }

    // メニュー
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.menu_setting -> {
            val intent = Intent(application, SettingActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.menu_licenses -> {
            val intent = Intent(application, OssLicensesMenuActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}

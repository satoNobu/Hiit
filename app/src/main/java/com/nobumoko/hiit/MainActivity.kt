package com.nobumoko.hiit

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    var tts:TextToSpeech? = null
    var repeatCnt:Long = 2
    val preparationTime: Long = 3
    val workTime: Long = 5
    val restTime: Long = 3

    private val TAG = "TestTTS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // TTS インスタンス生成
        tts = TextToSpeech(this, this)
        this.button.setOnClickListener {
            startCountDown()
        }

    }

    private fun startCountDown() {
//        val preparationTimer = countDownTimer(preparationTime)
        val workTimer = countDownTimer(workTime)
        val rest = countDownTimer(restTime)
        val result = Observable.concat(workTimer, rest)
        result
            .repeat(repeatCnt)
            .subscribe()
//        val result2 = Observable.concat(preparationTimer, result)
//        result2.subscribe()
    }

    private fun countDownTimer(count: Long): Observable<Long> {

        return  Observable.interval(1, TimeUnit.SECONDS)
            .take(count + 1)
//            .map { aLong -> count - aLong }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.i("Test", "Sstrt " + it.toString())
                speechText("Start")
                progressBar.max = count.toInt()
                progressBar.progress = count.toInt()
            }
            .doOnNext {
                Log.i("Test", it.toString())
                if ((count - it.toInt()) <= 3 ) {
                    speechText((count - it.toInt()).toString())
                }
                progressBar.progress = it.toInt()
                txtProgress.setText((count - it.toInt()).toString())
            }
            .doOnComplete{
                Log.i("Test", "完了")
                txtProgress.setText(0.toString())
                progressBar.progress = 0
                Toast.makeText(getApplicationContext(), "カウント終了" , Toast.LENGTH_SHORT).show();
            }
    }
    // 音声読み上げ
    override fun onInit(status: Int) {
        // TTS初期化
        if (TextToSpeech.SUCCESS == status) {
            Log.d(TAG, "initialized");
        } else {
            Log.e(TAG, "failed to initialize");
        }
    }

    private  fun shutDown() {
        tts?.shutdown()
    }

    override fun onDestroy() {
        super.onDestroy()
        shutDown()
    }

    private fun speechText(msg: String) {
        tts?.let {
            if (it.isSpeaking) return

            // 読み上げのスピード
            it.setSpeechRate(1.0f)
            // 読み上げのピッチ
            it.setPitch(1.0f)

            if (Build.VERSION.SDK_INT >= 21 ) {
                @Suppress("IMPLICIT_CAST_TO_ANY")
                it.speak(msg,  TextToSpeech.QUEUE_FLUSH, null, "messageID")
            } else {
                val map = HashMap<String, String>()
                @Suppress("IMPLICIT_CAST_TO_ANY")
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID")
                it.speak(msg,  TextToSpeech.QUEUE_FLUSH, map)
            }
            setTtsListener(it)
        }
    }
    // 読み上げの始まりと終わりを取得
    private fun setTtsListener(tts:TextToSpeech) {

        val listenerResult =
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onDone(utteranceId: String) {
                    Log.d(TAG, "progress on Done $utteranceId")
                }

                override fun onError(utteranceId: String) {
                    Log.d(TAG, "progress on Error $utteranceId")
                }

                override fun onStart(utteranceId: String) {
                    Log.d(TAG, "progress on Start $utteranceId")
                }
            })
    }
    // メニュー
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_favorite -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            val context: Context = applicationContext
            Toast.makeText(context , "favorite", Toast.LENGTH_LONG).show()
            val intent = Intent(application, SettingActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}

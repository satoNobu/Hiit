package com.nobumoko.hiit.Model

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.*
import kotlin.collections.HashMap

class TextToSpeech(
    private val pre: SettingPreferenceRepository
) : TextToSpeech.OnInitListener {

    private val TAG = "Log_TTS"
    var tts: TextToSpeech? = null
    fun init(context: Context) {
        tts = TextToSpeech(context, this)
    }

    // 音声読み上げ
    override fun onInit(status: Int) {
        // TTS初期化
        if (TextToSpeech.SUCCESS == status) {
            Log.d(TAG, "initialized" + tts?.voices);
            val english = Locale.ENGLISH
            if (tts?.isLanguageAvailable(english)!! >= TextToSpeech.LANG_AVAILABLE) {
                tts?.setLanguage(english)
                pre.saveInitTTS(true)
            } else {
                pre.saveInitTTS(false)
                Log.e(TAG, "Error SetLocale");
            }
        } else {
            pre.saveInitTTS(false)
            Log.e(TAG, "failed to initialize");
        }
    }

    fun shutDown() {
        tts?.shutdown()
    }

    fun speechText(msg: String) {
        tts?.let {
            if (it.isSpeaking) return

            // 読み上げのスピード
            it.setSpeechRate(1.0f)
            // 読み上げのピッチ
            it.setPitch(1.0f)

            if (Build.VERSION.SDK_INT >= 21) {
                @Suppress("IMPLICIT_CAST_TO_ANY")
                it.speak(msg, TextToSpeech.QUEUE_FLUSH, null, "messageID")
            } else {
                val map = HashMap<String, String>()
                @Suppress("IMPLICIT_CAST_TO_ANY")
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID")
                it.speak(msg, TextToSpeech.QUEUE_FLUSH, map)
            }
            setTtsListener(it)
        }
    }

    // 読み上げの始まりと終わりを取得
    private fun setTtsListener(tts: TextToSpeech) {
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
}
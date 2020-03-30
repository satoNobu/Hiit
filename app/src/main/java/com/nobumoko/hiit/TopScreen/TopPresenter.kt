package com.nobumoko.hiit.TopScreen

import android.content.Context
import com.nobumoko.hiit.Model.Constants
import com.nobumoko.hiit.Model.SettingPreferenceRepository
import com.nobumoko.hiit.Model.TextToSpeech
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TopPresenter(
    private val view: TopContract.View,
    private val tts: TextToSpeech,
    private val pre: SettingPreferenceRepository
) : TopContract.Presenter {

    override fun init(context: Context) {
        tts.init(context)
        view.setSettingData(
            pre.getWorkTime(),
            pre.getRestTime(),
            pre.getSetCount()
        )
    }

    override fun startWorkUpBtn(workTime: Int, restTime: Int, setCount: Int) {
        var count: Int = 0
        val workTimer = countDownTimer(workTime.toLong(), Constants.HiitState.WORK)
        val restTimeer = countDownTimer(restTime.toLong(), Constants.HiitState.REST)
        val result = Observable.interval(1, TimeUnit.SECONDS)
            .take(4)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                view.setTxtReady(3 - it)
            }
            .doOnComplete {
                view.readyEnd()
                Observable.concat(workTimer, restTimeer)
                    .doOnSubscribe {
                        count += 1
                        view.setCountNow(count)
                        view.repeatStart()
                    }
                    .doOnComplete {
                        view.workEnd()
                    }
                    .repeat(setCount.toLong())
                    .subscribe()
            }
        result.subscribe()
    }

    private fun countDownTimer(count: Long, state: Constants.HiitState): Observable<Long> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .take(count + 1)
//            .map { aLong -> count - aLong }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.setBackColor(state)
                view.setMaxValueOfProgressBar(count.toInt())
                view.setProgressValueOfProgressBar(count.toInt())
                view.showToast(state.toString() + " Start")
                speechText(state.toString() + "Start")
            }
            .doOnNext {
                if ((count - it.toInt()) <= 3) {
                    speechText((count - it.toInt()).toString())
                }
                view.setProgressValueOfProgressBar(it.toInt())
                view.showCountDownTimer((count - it.toInt()).toString())
            }
            .doOnComplete {
                view.showCountDownTimer(0.toString())
            }
    }

    override fun stopTts() {
        tts.shutDown()
    }

    private fun speechText(speechStr: String) {
        if (pre.getInitTTS() && pre.getUserTTS()) {
            tts.speechText(speechStr)
        }
    }
}
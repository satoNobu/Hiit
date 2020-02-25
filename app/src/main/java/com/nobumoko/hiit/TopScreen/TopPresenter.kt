package com.nobumoko.hiit.TopScreen

import android.util.Log
import com.nobumoko.hiit.Model.TextToSpeech
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

public class TopPresenter(
    private val view: TopContract.View,
    private val tts: TextToSpeech
) : TopContract.Presenter {

    override fun startWorkUpBtn(workTime: Int, restTime: Int, setCount: Int) {
        val workTimer = countDownTimer(workTime.toLong())
        val rest = countDownTimer(restTime.toLong())
        val result = Observable.concat(workTimer, rest)
        result
            .repeat(setCount.toLong())
            .subscribe()
    }

    private fun countDownTimer(count: Long): Observable<Long> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .take(count + 1)
//            .map { aLong -> count - aLong }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.i("Test", "Sstrt " + it.toString())
                tts.speechText("Start")
                view.setMaxValueOfProgressBar(count.toInt())
                view.setProgressValueOfProgressBar(count.toInt())
            }
            .doOnNext {
                Log.i("Test", it.toString())
                if ((count - it.toInt()) <= 3) {
                    tts.speechText((count - it.toInt()).toString())
                }
                view.setProgressValueOfProgressBar(it.toInt())
                view.showCountDownTimer((count - it.toInt()).toString())
            }
            .doOnComplete {
                Log.i("Test", "完了")
                view.setProgressValueOfProgressBar(0)
                view.showCountDownTimer(0.toString())
                view.showToast("カウントダウン終了")
            }
    }

    override fun stopTts() {
        tts.shutDown()
    }
}
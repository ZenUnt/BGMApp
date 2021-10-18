package com.websarva.wings.android.bgmapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder

class SoundManageService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    // メディアプレーヤープロパティ
    private var _player: MediaPlayer? = null

    override fun onCreate() {
        // プロパティのメディアプレーヤーオブジェクトを生成
        _player = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 音声ファイルのURI文字列を作成
        val mediaFileUriStr = "android.resource://${packageName}/${R.raw.rain}"
        // 音声ファイルのURI文字列をもとにURIオブジェクトを生成
        val mediaFileUri = Uri.parse(mediaFileUriStr)
        _player?.let {
            // メディアプレーヤーに音声ファイルを指定
            it.setDataSource(this@SoundManageService, mediaFileUri)
            // 非同期でのメディア再生準備が完了した際のリスナを設定
            it.setOnPreparedListener(PlayerPreparedListener())
            // メディア再生が終了した際のリスナを設定
            it.setOnCompletionListener(PlayerCompletionListener())
            // 非同期でメディア再生を準備
            it.prepareAsync()
        }
        // 定数を返す
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        _player?.let {
            if (it.isPlaying) {
                it.stop()
            }
            // プレーヤーを解放
            it.release()
        }
        _player = null
    }

    // メディア再生準備が完了した時のリスナクラス
    private inner class PlayerPreparedListener: MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer) {
            mp.start()
        }
    }

    //メディア再生が終了した時のリスナクラス
    private inner class Player inner class PlayerCompletionListener: MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer?) {
            stopSelf()
        }
    }
 }
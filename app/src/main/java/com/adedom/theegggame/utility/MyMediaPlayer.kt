package com.adedom.theegggame.utility

import android.content.Context
import android.media.MediaPlayer

class MyMediaPlayer {

    companion object {
        var mMusic: MediaPlayer? = null
        var mSound: MediaPlayer? = null

        fun getMusic(context: Context, music: Int): MediaPlayer {
            mMusic = if (mMusic == null) {
                MediaPlayer.create(context, music)
            } else {
                mMusic!!.stop()
                MediaPlayer.create(context, music)
            }
            mMusic!!.start()
            mMusic!!.isLooping = true
            return mMusic as MediaPlayer
        }

        fun getSound(context: Context, sound: Int): MediaPlayer {
            mSound = MediaPlayer.create(context,sound)
            mSound!!.start()
            return mSound as MediaPlayer
        }
    }
}

package com.adedom.theegggame.util

import android.content.Context
import android.media.MediaPlayer

class MyMediaPlayer {

    companion object {
        var music: MediaPlayer? = null
        var sound: MediaPlayer? = null

        fun getMusic(context: Context, music: Int): MediaPlayer {
            Companion.music = if (Companion.music == null) {
                MediaPlayer.create(context, music)
            } else {
                Companion.music!!.stop()
                MediaPlayer.create(context, music)
            }
            Companion.music!!.start()
            Companion.music!!.isLooping = true
            return Companion.music as MediaPlayer
        }

        fun getSound(context: Context, sound: Int): MediaPlayer {
            Companion.sound = MediaPlayer.create(context,sound)
            Companion.sound!!.start()
            return Companion.sound as MediaPlayer
        }
    }
}

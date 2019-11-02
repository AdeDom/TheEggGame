package com.adedom.theegggame.utility

import android.content.Context
import android.media.MediaPlayer

class MyMediaPlayer {

    companion object {
        var music: MediaPlayer? = null
        var sound: MediaPlayer? = null

        fun getMusic(context: Context, music: Int): MediaPlayer {
            this.music = if (this.music == null) {
                MediaPlayer.create(context, music)
            } else {
                this.music!!.stop()
                MediaPlayer.create(context, music)
            }
            this.music!!.start()
            this.music!!.isLooping = true
            return this.music as MediaPlayer
        }

        fun getSound(context: Context, sound: Int): MediaPlayer {
            this.sound = MediaPlayer.create(context,sound)
            this.sound!!.start()
            return this.sound as MediaPlayer
        }
    }
}

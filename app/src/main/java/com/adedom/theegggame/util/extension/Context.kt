package com.adedom.theegggame.util.extension

import android.content.Context
import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.playMusic
import com.adedom.library.util.playSound
import com.adedom.library.util.stopMusic
import com.adedom.theegggame.R
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.SOUND_MUSIC
import com.adedom.theegggame.util.SOUND_MUSIC_OFF
import com.adedom.theegggame.util.SOUND_MUSIC_ON

fun Context.playSoundClick() {
    if (this.readPrefFile(SOUND_MUSIC) == SOUND_MUSIC_ON) playSound(this, R.raw.sound_click)
}

fun Context.playSoundKeep() {
    if (this.readPrefFile(SOUND_MUSIC) == SOUND_MUSIC_ON) playSound(this, R.raw.sound_keep)
}

fun Context.playMusicGame() {
    if (this.readPrefFile(SOUND_MUSIC) == SOUND_MUSIC_ON)
        playMusic(GameActivity.sContext, R.raw.music)
}

fun Context.setSoundMusic(on: (() -> Unit)? = null, off: (() -> Unit)? = null) {
    if (this.readPrefFile(SOUND_MUSIC) == SOUND_MUSIC_OFF) {
        this.writePrefFile(SOUND_MUSIC, SOUND_MUSIC_ON)
        playMusic(this, R.raw.music)
        on?.invoke()
    } else {
        this.writePrefFile(SOUND_MUSIC, SOUND_MUSIC_OFF)
        stopMusic()
        off?.invoke()
    }
}



package com.adedom.theegggame.util.extension

import android.content.Context
import com.adedom.library.extension.getPrefFile
import com.adedom.library.util.playSound
import com.adedom.theegggame.R
import com.adedom.theegggame.util.SOUND_MUSIC
import com.adedom.theegggame.util.SOUND_MUSIC_ON

fun Context.playSoundClick() {
    if (this.getPrefFile(SOUND_MUSIC) == SOUND_MUSIC_ON) playSound(this, R.raw.sound_click)
}

fun Context.playSoundKeep() {
    if (this.getPrefFile(SOUND_MUSIC) == SOUND_MUSIC_ON) playSound(this, R.raw.sound_keep)
}


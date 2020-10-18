package com.adedom.teg.sharedpreference.service

import android.content.Context
import androidx.core.content.edit

class PreferenceConfigImpl(context: Context) : PreferenceConfig {

    private val sharedPreference: android.content.SharedPreferences =
        context.getSharedPreferences(PREF_CONFIG, Context.MODE_PRIVATE)

    override var username: String
        get() = sharedPreference.getString(KEY_USERNAME, "").orEmpty()
        set(value) {
            sharedPreference.edit {
                putString(KEY_USERNAME, value)
            }
        }

    companion object {
        const val PREF_CONFIG = "pref_config"
        const val KEY_USERNAME = "username"
    }

}

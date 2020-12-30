package com.leafy.githubsearcher.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.leafy.githubsearcher.R

object StartupTheme {
    fun getTheme(context: Context) {
        val themeKey = context.resources.getString(R.string.themeKey)
        val themeDefault = context.resources.getString(R.string.themeValue_default)
        val themeDay = context.resources.getString(R.string.themeValue_day)
        val themeNight = context.resources.getString(R.string.themeValue_night)
        val themeAuto = context.resources.getString(R.string.themeValue_auto)

        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val themeValue = preference.getString(themeKey, themeDefault)

        // Get the theme values
        AppCompatDelegate.setDefaultNightMode(
            when (themeValue) {
                themeDefault -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                themeDay -> AppCompatDelegate.MODE_NIGHT_NO
                themeNight -> AppCompatDelegate.MODE_NIGHT_YES
                themeAuto -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                else -> throw Throwable("Unknown theme value: $themeValue")
            }
        )
    }
}
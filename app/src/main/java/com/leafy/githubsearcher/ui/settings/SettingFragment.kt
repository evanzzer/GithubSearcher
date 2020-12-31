package com.leafy.githubsearcher.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.leafy.githubsearcher.R

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var themePref: ListPreference? = null

    private lateinit var themeKey: String
    private lateinit var themeDefaultKey: String
    private lateinit var themeDayKey: String
    private lateinit var themeNightKey: String
    private lateinit var themeAutoKey: String

    private lateinit var themeDefaultValue: String
    private lateinit var themeDayValue: String
    private lateinit var themeNightValue: String
    private lateinit var themeAutoValue: String

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preference, rootKey)

        themeKey = resources.getString(R.string.themeKey)

        themeDefaultKey = getString(R.string.theme_default)
        themeDayKey = getString(R.string.theme_day)
        themeNightKey = getString(R.string.theme_night)
        themeAutoKey = getString(R.string.theme_auto)

        themeDefaultValue = getString(R.string.themeValue_default)
        themeDayValue = getString(R.string.themeValue_day)
        themeNightValue = getString(R.string.themeValue_night)
        themeAutoValue = getString(R.string.themeValue_auto)

        themePref = findPreference(themeKey)

        //Set Summary
        val pref = preferenceManager.sharedPreferences
        val themeValue = (pref.getString(themeKey, themeDefaultValue))

        when {
            themeValue.equals(themeDefaultValue) -> {
                //if getting default value
                themePref?.value = themeDefaultValue
                themePref?.summary = themeDefaultKey
            }
            themeValue.equals(themeDayValue) -> themePref?.summary = themeDayKey
            themeValue.equals(themeNightValue) -> themePref?.summary = themeNightKey
            themeValue.equals(themeAutoValue) -> themePref?.summary = themeAutoKey
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == themeKey) {
            when (themePref?.value) {
                themeDefaultValue -> {
                    //Default Theme
                    themePref?.summary = themeDefaultKey
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                themeDayValue -> {
                    //Day Theme
                    themePref?.summary = themeDayKey
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                themeNightValue -> {
                    //Night Theme
                    themePref?.summary = themeNightKey
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                themeAutoValue -> {
                    //Auto Theme
                    themePref?.summary = themeAutoKey
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}
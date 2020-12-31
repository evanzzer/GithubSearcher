package com.leafy.githubsearcher.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leafy.githubsearcher.R
import com.leafy.githubsearcher.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.title = resources.getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settingContainer, SettingFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
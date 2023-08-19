package com.example.playlistmakervmp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
//Все изменения на 12 спринт тут, но что то у меня непонятное с коммитами произошло, прошу прощения!

class App: Application() {
    var darkTheme = false
    override fun onCreate() {
        super.onCreate()
        val sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("isDarkMode", false)
        switchTheme(isDarkMode)
    }
    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    fun getCurrentTheme(): Boolean {
        val sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("isDarkMode", false)
    }
}
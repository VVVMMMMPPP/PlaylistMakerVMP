package com.example.playlistmakervmp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener { finish() }


        val themeSwitcher = findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.isChecked = (applicationContext as App).getCurrentTheme()
        themeSwitcher.setOnCheckedChangeListener { themeSwitcher, checked ->
            val sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            (applicationContext as App).switchTheme(checked)
            sharedPrefs.edit()
                .putBoolean("isDarkMode", checked)
                .apply()
        }

        val shareApp = findViewById<TextView>(R.id.shareApp)
        shareApp.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.linkCourse))
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, "Share"))
        }
        val supportButton = findViewById<TextView>(R.id.supportButton)
        supportButton.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.myEMail)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSubject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.emailText))
                startActivity(Intent.createChooser(this, "Support"))
            }

            val policyButton = findViewById<TextView>(R.id.policyButton)
            policyButton.setOnClickListener {
                val policyIntent = Intent(Intent.ACTION_VIEW)
                policyIntent.data = Uri.parse(getString(R.string.linkLegacy))
                startActivity(Intent.createChooser(policyIntent, "Policy"))
            }
        }
    }
}

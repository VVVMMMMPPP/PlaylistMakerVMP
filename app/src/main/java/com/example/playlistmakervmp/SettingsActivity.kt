package com.example.playlistmakervmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener { val displayIntent= Intent(this,MainActivity::class.java)
            startActivity(displayIntent)  }
    }
}
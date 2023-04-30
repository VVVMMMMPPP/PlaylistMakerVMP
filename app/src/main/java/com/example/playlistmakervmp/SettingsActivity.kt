package com.example.playlistmakervmp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener { val displayIntent= Intent(this,MainActivity::class.java)
            startActivity(displayIntent)  }

        val shareApp= findViewById<TextView>(R.id.shareApp)
        shareApp.setOnClickListener {
            val sendIntent= Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.linkCourse))
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, "Share"))
        }
        val supportButton = findViewById<TextView>(R.id.supportButton)
        supportButton.setOnClickListener {
            val shareIntent= Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.myEMail))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSubject))
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailText))
            startActivity(Intent.createChooser(shareIntent,"Support"))
        }
        val policyButton=findViewById<TextView>(R.id.policyButton)
        policyButton.setOnClickListener {
            val policyIntent=Intent(Intent.ACTION_VIEW)
            policyIntent.data=Uri.parse(getString(R.string.linkLegacy))
            startActivity(Intent.createChooser(policyIntent,"Policy"))
        }
    }

}

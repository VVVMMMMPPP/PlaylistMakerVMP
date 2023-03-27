package com.example.playlistmakervmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.search)
        val buttonSearchClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Тут будет поиск!", Toast.LENGTH_SHORT).show()
            }
        }
        buttonSearch.setOnClickListener(buttonSearchClickListener)


        val buttonMedia=findViewById<Button>(R.id.media)
        buttonMedia.setOnClickListener { Toast.makeText(this,"Тут будет Медиа!", Toast.LENGTH_SHORT).show() }

        val buttonSettings=findViewById<Button>(R.id.settings)
        buttonSettings.setOnClickListener { Toast.makeText(this,"Тут будут настройки!", Toast.LENGTH_SHORT).show() }
    }
}
package com.example.playlistmakervmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        val backImage = findViewById<ImageView>(R.id.backButton)
        backImage.setOnClickListener {
            finish()
        }

        val sharedPrefs = getSharedPreferences("prefs_track", MODE_PRIVATE)
        val trackString = sharedPrefs.getString("MEDIA", "")

        val track = Gson().fromJson(trackString, Track::class.java)
        val trackName = findViewById<TextView>(R.id.track_name)
        val trackArtist = findViewById<TextView>(R.id.track_artist)
        val trackDuration = findViewById<TextView>(R.id.track_duration)
        val trackAlbum = findViewById<TextView>(R.id.track_album)
        val trackGenre = findViewById<TextView>(R.id.track_genre)
        val trackCountry = findViewById<TextView>(R.id.track_country)
        val trackCover = findViewById<ImageView>(R.id.track_cover)
        val album = findViewById<TextView>(R.id.textview2)

        val cover = track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
        Glide.with(this).load(cover)
            .centerCrop()
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corners_cover)))
            .into(trackCover)
        trackName.text = track.trackName
        trackArtist.text = track.artistName
        trackDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toLong())
        trackAlbum.text = track.collectionName
        if (trackAlbum.text == "") {
            trackAlbum.visibility = View.GONE
            album.visibility = View.GONE
        } else {
            trackAlbum.visibility = View.VISIBLE
            album.visibility = View.VISIBLE
        }


        val releaseDateStr = track.releaseDate


        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val releaseDate = inputFormat.parse(releaseDateStr)


        val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val formattedDate = outputFormat.format(releaseDate)
        println(formattedDate)

        trackGenre.text = track.primaryGenreName
        trackCountry.text = track.country
    }
}
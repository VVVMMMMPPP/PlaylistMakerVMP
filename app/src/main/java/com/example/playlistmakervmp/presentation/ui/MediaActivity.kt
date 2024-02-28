package com.example.playlistmakervmp.presentation.ui

import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakervmp.R
import com.example.playlistmakervmp.domain.model.Track
import com.example.playlistmakervmp.presentation.MediaView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class MediaActivity: AppCompatActivity(), MediaContract {
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 300L
    }

    private var playerState = STATE_DEFAULT
    private lateinit var trackLength: TextView
    private var mediaPlayer = MediaPlayer()
    private lateinit var playButton: ImageView
    private lateinit var sharedPrefs: SharedPreferences
    private var trackString: String? = ""
    private lateinit var track: Track
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (mediaPlayer.isPlaying) {
                trackLength.text = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(mediaPlayer.currentPosition)
                handler.postDelayed(this, DELAY)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        playButton = findViewById(R.id.playButton)
        sharedPrefs = getSharedPreferences("prefs_track", MODE_PRIVATE)
        trackString = sharedPrefs.getString("MEDIA", "")
        track = Gson().fromJson(trackString, Track::class.java)

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
        val trackYear = findViewById<TextView>(R.id.track_year)
        val trackGenre = findViewById<TextView>(R.id.track_genre)
        val trackCountry = findViewById<TextView>(R.id.track_country)
        val trackCover = findViewById<ImageView>(R.id.track_cover)
        val album = findViewById<TextView>(R.id.textview2)
        trackLength = findViewById(R.id.track_length)

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
        trackYear.text = formattedDate

        preparePlayer()
        playButton.setOnClickListener {
            playbackControl()
        }

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
            playButton.setImageResource(R.drawable.button_play)
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
            playButton.setImageResource(R.drawable.button_play)
            trackLength.text = "00:00"
            handler.removeCallbacks(runnable)
        }
    }
    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.button_pause)
        playerState = STATE_PLAYING
        handler.post(runnable)

    }
    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.button_play)
        playerState = STATE_PAUSED
        handler.removeCallbacks(runnable)
    }
    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
}
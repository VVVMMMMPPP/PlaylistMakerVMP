package com.example.playlistmakervmp.presentation

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.playlistmakervmp.domain.PlayerInteractor
import com.example.playlistmakervmp.presentation.ui.MediaContract

class MediaPresenter(
    private val view: MediaView,
    private val previewUrl: String?,
    private val playerInteractor: PlayerInteractor,
) : MediaContract.Presenter {
    private val progressHandler = Handler(Looper.getMainLooper())
    private lateinit var progressRunnable: Runnable

    init {
        progressRunnable = Runnable {
            updateProgressTime()
            progressHandler.postDelayed(progressRunnable, 300)
        }

        playerInteractor.preparePlayer(previewUrl ?: "", {}, {
            view.updateProgressTime("00:00")
            progressHandler.removeCallbacks(progressRunnable)
            view.showPlayButton()
        })
    }

    private fun updateProgressTime() {
        val progress = playerInteractor.currentPosition()
        view.updateProgressTime(formatTime(progress))
    }

    private fun formatTime(timeInMillis: Int): String {
        val minutes = timeInMillis / 1000 / 60
        val seconds = timeInMillis / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onPlayClicked() {
        view.showPauseButton()
        playerInteractor.startAudio()
        progressHandler.post(progressRunnable)
    }

    override fun onPauseAudioClicked() {
        view.showPlayButton()
        playerInteractor.pauseAudio()
        progressHandler.removeCallbacks(progressRunnable)
    }

    override fun onPause() {
        view.showPlayButton()
        playerInteractor.pauseAudio()
    }
}
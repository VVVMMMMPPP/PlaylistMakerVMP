package com.example.playlistmakervmp.presentation.ui

interface MediaContract {
    interface Presenter {
        fun onPlayClicked()
        fun onPauseAudioClicked()
        fun onPause()

    }
}
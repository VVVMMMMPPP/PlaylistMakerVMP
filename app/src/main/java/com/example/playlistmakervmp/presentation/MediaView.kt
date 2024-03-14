package com.example.playlistmakervmp.presentation

interface MediaView {
    fun updateProgressTime(time: String)
    fun showPlayButton()
    fun showPauseButton()
}
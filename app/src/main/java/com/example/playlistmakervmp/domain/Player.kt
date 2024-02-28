package com.example.playlistmakervmp.domain

interface Player {
    fun startAudio()
    fun pauseAudio()
    fun isPlaying(): Boolean
    fun currentPosition(): Int
    fun playbackControl(onStartPlayer: () -> Unit, onPausePlayer: () -> Unit)
    fun preparePlayer(
        dataSource: String,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    )
}
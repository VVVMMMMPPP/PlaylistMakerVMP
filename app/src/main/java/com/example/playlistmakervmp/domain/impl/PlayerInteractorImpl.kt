package com.example.playlistmakervmp.domain.impl

import com.example.playlistmakervmp.domain.Player
import com.example.playlistmakervmp.domain.PlayerInteractor

class PlayerInteractorImpl(private val player: Player): PlayerInteractor {
    override fun startAudio() {
        player.startAudio()
    }

    override fun pauseAudio() {
        player.pauseAudio()
    }

    override fun isPlaying(): Boolean {
        return player.isPlaying()
    }

    override fun currentPosition(): Int {
        return player.currentPosition()
    }

    override fun preparePlayer(
        dataSource: String,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    ) {
        player.preparePlayer(dataSource, onPreparedListener, onCompletionListener)
    }

    override fun playbackControl(onStartPlayer: () -> Unit, onPausePlayer: () -> Unit) {
        player.playbackControl(onStartPlayer, onPausePlayer)
    }
}
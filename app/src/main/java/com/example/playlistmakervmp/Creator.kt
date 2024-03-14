package com.example.playlistmakervmp

import android.content.Intent
import com.example.playlistmakervmp.data.PlayerImpl
import com.example.playlistmakervmp.data.dto.MediaDataSource
import com.example.playlistmakervmp.domain.PlayerInteractor
import com.example.playlistmakervmp.domain.api.MediaRepository
import com.example.playlistmakervmp.domain.impl.PlayerInteractorImpl

object Creator {
    fun createMediaRepository(intent: Intent): MediaRepository {
        return MediaDataSource(intent)
    }

    fun createInteractor(audioUrl: String?): PlayerInteractor {
        val player = PlayerImpl(audioUrl)
        return PlayerInteractorImpl(player)
    }
}
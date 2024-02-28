package com.example.playlistmakervmp.domain.model

import com.example.playlistmakervmp.domain.model.Track

data class TracksResponse(
    val resultCount: Int,
    val results: List<Track>
)
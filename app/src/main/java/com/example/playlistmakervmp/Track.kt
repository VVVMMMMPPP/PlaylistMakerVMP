package com.example.playlistmakervmp

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val artworkUrl100: String
)
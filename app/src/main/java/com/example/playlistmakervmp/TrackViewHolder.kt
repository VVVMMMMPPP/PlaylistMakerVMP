package com.example.playlistmakervmp

import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.Locale

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val sourceName: TextView = itemView.findViewById(R.id.track_name)
    private val artistName: TextView = itemView.findViewById(R.id.track_artist)
    private val trackLength: TextView = itemView.findViewById(R.id.track_length)
    private val image: ImageView = itemView.findViewById(R.id.track_image)

    fun bind(model: Track) {
        sourceName.text = model.trackName
        artistName.text = model.artistName
        trackLength.text = SimpleDateFormat("mm:ss", Locale.getDefault())
            .format(model.trackTimeMillis.toLong())

        Glide.with(itemView.context).load(model.artworkUrl100)
            .centerCrop()// Отрисовка фотографии артиста с помощью библиотеки Glide
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corners)))
            .into(image)
    }
}
package com.example.playlistmakervmp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace.Model
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.util.Objects
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakervmp.TrackAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Tag


class SearchActivity : AppCompatActivity() {
    private var tracks = mutableListOf<Track>()
    private var history = mutableListOf<Track>()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   private val appleApiService = retrofit.create<AppleApiService>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val sharedPrefs = getSharedPreferences("prefs_track", MODE_PRIVATE)
        var json = sharedPrefs.getString("TRACKS", "")
        val listType = object : TypeToken<MutableList<Track>>() {}.type
        try {
            history = Gson().fromJson(json, listType)
        } catch (e:Exception){
        }

        val searched = findViewById<TextView>(R.id.searched)
        val clearHistory = findViewById<Button>(R.id.clear_history)
        val back = findViewById<ImageView>(R.id.back)
        val clearText = findViewById<ImageView>(R.id.clear_text)
        val editText = findViewById<EditText>(R.id.SearchEditText)
        val placeholderError = findViewById<ImageView>(R.id.placeholder_error)
        val placeholderErrorText = findViewById<TextView>(R.id.placeholder_error_text)
        val placeholderErrorButton = findViewById<Button>(R.id.placeholder_error_button)
        val placeholderNothingFound = findViewById<ImageView>(R.id.placeholder_nothing_found)
        val placeholderNothingFoundText =
            findViewById<TextView>(R.id.placeholder_nothing_found_text)

        val historyAdapter = TrackAdapter(history)
        val trackAdapter = TrackAdapter(tracks)
        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        recyclerView.adapter = trackAdapter







        back.setOnClickListener {
            back.setOnClickListener { finish() }
        }

        clearText.setOnClickListener {
            editText.setText("")
            tracks.clear()
            trackAdapter.notifyDataSetChanged()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
            placeholderError.visibility = View.GONE
            placeholderErrorButton.visibility = View.GONE
            placeholderErrorText.visibility = View.GONE
            placeholderNothingFound.visibility = View.GONE
            placeholderNothingFoundText.visibility = View.GONE
            json = sharedPrefs.getString("TRACKS", "")
            history = Gson().fromJson(json, listType)
            recyclerView.adapter = historyAdapter
            historyAdapter.notifyDataSetChanged()
        }



        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearText.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                if (history.size > 0) {

                    searched.visibility = if (editText.hasFocus() and (s?.isEmpty() == true)) View.VISIBLE else View.GONE
                    clearHistory.visibility =
                        if (editText.hasFocus() and (s?.isEmpty() == true)) View.VISIBLE else View.GONE
                    recyclerView.visibility =
                        if (editText.hasFocus() and (s?.isEmpty() == true)) View.VISIBLE else View.GONE
                    json = sharedPrefs.getString("TRACKS", "")
                    history = Gson().fromJson(json, listType)
                    recyclerView.adapter = historyAdapter
                    historyAdapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {

                clearText.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                if (history.size > 0) {

                    searched.visibility = if (editText.hasFocus() and (s?.isEmpty() == true)) View.VISIBLE else View.GONE
                    clearHistory.visibility =
                        if (editText.hasFocus() and (s?.isEmpty() == true)) View.VISIBLE else View.GONE
                    recyclerView.visibility =
                        if (editText.hasFocus() and (s?.isEmpty() == true)) View.VISIBLE else View.GONE
                    json = sharedPrefs.getString("TRACKS", "")
                    history = Gson().fromJson(json, listType)
                    recyclerView.adapter = historyAdapter
                    historyAdapter.notifyDataSetChanged()
                }

            }
        })

        fun searchTracks() {
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = trackAdapter
            appleApiService.search(editText.text.toString())
                .enqueue(object : Callback<TracksResponse> {

                    override fun onResponse(
                        call: retrofit2.Call<TracksResponse>,
                        response: Response<TracksResponse>
                    ) {
                        if (response.code() == 200) {

                            tracks.clear()
                            trackAdapter.notifyDataSetChanged()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                placeholderError.visibility = View.GONE
                                placeholderErrorButton.visibility = View.GONE
                                placeholderErrorText.visibility = View.GONE
                                placeholderNothingFound.visibility = View.GONE
                                placeholderNothingFoundText.visibility = View.GONE
                                tracks.addAll(response.body()?.results!!)
                                trackAdapter.notifyDataSetChanged()
                            } else {
                                runOnUiThread {
                                    tracks.clear()
                                    trackAdapter.notifyDataSetChanged()
                                    placeholderError.visibility = View.GONE
                                    placeholderErrorButton.visibility = View.GONE
                                    placeholderErrorText.visibility = View.GONE
                                    placeholderNothingFound.visibility = View.VISIBLE
                                    placeholderNothingFoundText.visibility = View.VISIBLE
                                }
                            }
                        } else {
                            tracks.clear()
                            trackAdapter.notifyDataSetChanged()
                            runOnUiThread {
                                placeholderError.visibility = View.VISIBLE
                                placeholderErrorButton.visibility = View.VISIBLE
                                placeholderErrorText.visibility = View.VISIBLE
                                placeholderNothingFound.visibility = View.GONE
                                placeholderNothingFoundText.visibility = View.GONE
                            }
                        }
                    }


                    override fun onFailure(call: retrofit2.Call<TracksResponse>, t:Throwable) {
                        runOnUiThread {
                            tracks.clear()
                            trackAdapter.notifyDataSetChanged()
                            placeholderErrorText.setText(R.string.error400)
                            placeholderError.visibility = View.VISIBLE
                            placeholderErrorButton.visibility = View.VISIBLE
                            placeholderErrorText.visibility = View.VISIBLE
                            placeholderNothingFound.visibility = View.GONE
                            placeholderNothingFoundText.visibility = View.GONE
                        }
                    }

                })


        }
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                searchTracks()
                true
            }
            false
        }

        placeholderErrorButton.setOnClickListener {
            placeholderError.visibility = View.GONE
            placeholderErrorButton.visibility = View.GONE
            placeholderErrorText.visibility = View.GONE
            placeholderNothingFound.visibility = View.GONE
            placeholderNothingFoundText.visibility = View.GONE
            searchTracks()
        }

        trackAdapter.setOnTrackClickListener(object : OnTrackClickListener {
            override fun onTrackClick(position: Int) {
                val editor = sharedPrefs.edit()
                if ((history.size <= 9) and !isTrackInHistory(tracks[position])) {
                    history.add(0, tracks[position])
                    json = Gson().toJsonTree(history).asJsonArray.toString()
                    editor.putString("TRACKS", json).apply()
                }
                else if ((history.size == 10) and !isTrackInHistory(tracks[position])) {

                    history.add(0, tracks[position])
                    history.removeLast()
                    json = Gson().toJsonTree(history).asJsonArray.toString()
                    editor.putString("TRACKS", json).apply()
                }
                else if (isTrackInHistory(tracks[position])) {

                    lateinit var songbuf: Track
                    for (song in history) {
                        if (song.trackId == tracks[position].trackId) {
                            songbuf = song
                        }
                    }
                    history.remove(songbuf)
                    history.add(0, tracks[position])
                    json = Gson().toJsonTree(history).asJsonArray.toString()
                    editor.putString("TRACKS", json).apply()
                }
            }
        })
        editText.setOnFocusChangeListener { view, hasFocus ->
            if (history.size > 0) {

                searched.visibility = if (hasFocus and editText.text.isEmpty()) View.VISIBLE else View.GONE
                clearHistory.visibility =
                    if (hasFocus and editText.text.isEmpty()) View.VISIBLE else View.GONE
                recyclerView.visibility = if (hasFocus and editText.text.isEmpty()) View.VISIBLE else View.GONE
                json = sharedPrefs.getString("TRACKS", "")
                history = Gson().fromJson(json, listType)
                recyclerView.adapter = historyAdapter
                historyAdapter.notifyDataSetChanged()

            }
        }
        clearHistory.setOnClickListener {
            history.clear()
            historyAdapter.notifyDataSetChanged()
            sharedPrefs.edit().putString("TRACKS", "").apply()
            searched.visibility = View.GONE
            clearHistory.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }

    }
    fun isTrackInHistory(track: Track): Boolean {
        for (song in history) {
            if (track.trackId == song.trackId) return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val editText = findViewById<EditText>(R.id.SearchEditText)
        outState.putString("myKey", editText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val myValue = savedInstanceState.getString("myKey")
        val editText = findViewById<EditText>(R.id.SearchEditText)
        editText.setText(myValue)
    }

}







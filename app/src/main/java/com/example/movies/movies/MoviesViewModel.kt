package com.example.movies.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.helper.Constant.Companion.API_KEY
import com.example.movies.helper.Constant.Companion.BASE_URL
import com.example.movies.helper.Constant.Companion.LANGUAGE
import com.example.movies.model.Movies
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviesViewModel : ViewModel() {
    companion object {
        private val TAG = MoviesViewModel::class.java.simpleName
    }

    val listMovie = MutableLiveData<ArrayList<Movies>>()

    //function for set list genre
    fun seMovies(genreId: Int) {
        Log.d(TAG, "setMovies Starting...")
        //set array list movie
        val listDataMovie = ArrayList<Movies>()
        //set progress bar
        val client = AsyncHttpClient()
        val url =
            "$BASE_URL/3/discover/movie?api_key=$API_KEY&language=$LANGUAGE&with_genres=$genreId"

        //get client
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.i(TAG, "setMovies OnSuccess...")
                val result: String? = responseBody?.let { String(it) }
                result?.let { Log.d(TAG, it) }
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("results")
                    for (i in 0 until jsonArray.length()) {
                        val movieObject = jsonArray.getJSONObject(i)
                        val backdrop = movieObject.getString("backdrop_path")
                        val id = movieObject.getInt("id")
                        val originalLanguage = movieObject.getString("original_language")
                        val originalTitle = movieObject.getString("original_title")
                        val overview = movieObject.getString("overview")
                        val popularity = movieObject.getDouble("popularity")
                        val posterPath = movieObject.getString("poster_path")
                        val releaseDate = movieObject.getString("release_date")
                        val title = movieObject.getString("title")
                        val video = movieObject.getBoolean("video")
                        val voteAverage = movieObject.getDouble("vote_average")
                        val voteCount = movieObject.getInt("vote_count")
                        val movies = Movies(
                            backdrop,
                            id,
                            originalLanguage,
                            originalTitle,
                            overview,
                            popularity,
                            posterPath,
                            releaseDate,
                            title,
                            video,
                            voteAverage,
                            voteCount
                        )
                        listDataMovie.add(movies)
                    }
                    listMovie.postValue(listDataMovie)
                    Log.e(TAG, "set movies success")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "catch movies: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e(TAG, "onFailure movies: ${error?.message}")
            }

        })

    }

    //function for get list genre
    fun getMovies(): LiveData<ArrayList<Movies>> {
        return listMovie
    }
}
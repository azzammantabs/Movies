package com.example.movies.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.helper.Constant.Companion.API_KEY
import com.example.movies.helper.Constant.Companion.BASE_URL
import com.example.movies.helper.Constant.Companion.LANGUAGE
import com.example.movies.model.Genres
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class HomeViewModel : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    val listGenre = MutableLiveData<ArrayList<Genres>>()

    //function for set list genre
    fun setGenres() {
        Log.d(TAG, "setGenre Starting...")
        //set array list genre
        val listDataGenre = ArrayList<Genres>()
        //set progress bar
        val client = AsyncHttpClient()
        val url = "$BASE_URL/3/genre/movie/list?api_key=$API_KEY&language=$LANGUAGE"

        //get client
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.i(TAG, "setGenre OnSuccess...")
                val result = responseBody.toString()
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("genres")
                    for (i in 0 until jsonArray.length()) {
                        val genreObject = jsonArray.getJSONObject(i)
                        val id = genreObject.getInt("id")
                        val name = genreObject.getString("name")
                        val genre = Genres(id, name)
                        listDataGenre.add(genre)
                    }
                    listGenre.postValue(listDataGenre)
                    Log.e(TAG, "set genre success")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "catch genre: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e(TAG, "onFailure genre: ${error?.message}")
            }

        })

    }

    //function for get list genre
    fun getGenres(): LiveData<ArrayList<Genres>> {
        return listGenre
    }
}
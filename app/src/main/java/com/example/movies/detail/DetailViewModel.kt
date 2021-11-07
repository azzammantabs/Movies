package com.example.movies.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.helper.Constant.Companion.API_KEY
import com.example.movies.helper.Constant.Companion.BASE_URL
import com.example.movies.helper.Constant.Companion.LANGUAGE
import com.example.movies.model.Reviewer
import com.example.movies.model.Video
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    val listReview = MutableLiveData<ArrayList<Reviewer>>()
    val listVideo = MutableLiveData<ArrayList<Video>>()

    //function for set list review
    fun setReview(movieId: Int) {
        Log.d(TAG, "setReview Starting...")
        //set array list review
        val listDataReview = ArrayList<Reviewer>()
        val client = AsyncHttpClient()
        val url = "$BASE_URL/3/movie/$movieId/reviews?api_key=$API_KEY&language=$LANGUAGE&page=1"

        //get client
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.i(TAG, "setReview OnSuccess...")
                val result: String? = responseBody?.let { String(it) }
                result?.let { Log.d(TAG, it) }
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("results")
                    for (i in 0 until jsonArray.length()) {
                        val reviewObject = jsonArray.getJSONObject(i)
                        val id = reviewObject.getString("id")
                        val author = reviewObject.getString("author")
                        val avatarPath =
                            reviewObject.getJSONObject("author_details").getString("avatar_path")
                        val content = reviewObject.getString("content")
                        val reviewer = Reviewer(id, author, avatarPath, content)
                        listDataReview.add(reviewer)
                    }
                    listReview.postValue(listDataReview)
                    Log.e(TAG, "set review success")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "catch review: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e(TAG, "onFailure review: ${error?.message}")
            }

        })

    }

    //function for get list review
    fun getReview(): LiveData<ArrayList<Reviewer>> {
        return listReview
    }

    //function for set list video
    fun setVideo(movieId: Int) {
        Log.d(TAG, "setVideo Starting...")
        //set array list video
        val listDataVideo = ArrayList<Video>()
        val client = AsyncHttpClient()
        val url = "$BASE_URL/3/movie/$movieId/videos?api_key=$API_KEY&language=$LANGUAGE"

        //get client
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.i(TAG, "setVideo OnSuccess...")
                val result: String? = responseBody?.let { String(it) }
                result?.let { Log.d(TAG, it) }
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("results")
                    for (i in 0 until jsonArray.length()) {
                        val videoObject = jsonArray.getJSONObject(i)
                        val id = videoObject.getString("id")
                        val name = videoObject.getString("name")
                        val key = videoObject.getString("key")
                        val site = videoObject.getString("site")
                        val video = Video(id, name, key, site)
                        listDataVideo.add(video)
                    }
                    listVideo.postValue(listDataVideo)
                    Log.e(TAG, "set video success")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "catch video: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e(TAG, "onFailure video: ${error?.message}")
            }

        })

    }

    //function for get list video
    fun getVideo(): LiveData<ArrayList<Video>> {
        return listVideo
    }
}
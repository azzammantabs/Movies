package com.example.movies.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movies.adapter.ReviewAdapter
import com.example.movies.databinding.ActivityDetailBinding
import com.example.movies.helper.Constant.Companion.BASE_URL_IMAGE
import com.example.movies.helper.Constant.Companion.BASE_URL_YOUTUBE
import com.example.movies.model.Movies
import com.example.movies.model.Reviewer
import com.example.movies.model.Video
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer


class DetailActivity : AppCompatActivity() {

    companion object {
        private val TAG = DetailActivity::class.java.simpleName
        const val EXTRA_MOVIES = "extra_movies"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var listReviewer = ArrayList<Reviewer>()
    private var listVideo = ArrayList<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //set movie
        val movie = intent.getParcelableExtra<Movies>(EXTRA_MOVIES)!!

        //set toolbar
        binding.tlDetail.title = movie.title

        //setData()
        setData(movie)

        //set view model
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        //call set review
        detailViewModel.setReview(movie.id)

        //call get review
        detailViewModel.getReview().observe(this, Observer { listReviewer ->
            Log.i(TAG, "call getReview...")
            //check list review
            if (listReviewer != null) {
                Log.i(TAG, "info Review: $listReviewer")
                this.listReviewer = listReviewer
                //show review
                showReview()
                Log.i(TAG, "call review Success")
            } else {

            }
        })

        //call set video
        detailViewModel.setVideo(movie.id)

        //call get video
        detailViewModel.getVideo().observe(this, Observer { listVideo ->
            Log.i(TAG, "call getVideo...")
            //check list review
            if (listVideo != null) {
                Log.i(TAG, "info Video: $listVideo")
                this.listVideo = listVideo
                //play trailer video
                playVideo(listVideo[1])
                Log.i(TAG, "call Video Success")
            } else {

            }
        })
    }

    private fun setData(movies: Movies) {
        Glide.with(this)
            .load("$BASE_URL_IMAGE/${movies.backdrop_path}")
            .error(com.example.movies.R.drawable.ic_outline_image_24)
            .into(binding.ivMoviesDetail)
        Glide.with(this)
            .load("$BASE_URL_IMAGE/${movies.poster_path}")
            .error(com.example.movies.R.drawable.ic_outline_image_24)
            .into(binding.ivMoviesPosterDetail)

        binding.tvTitleDetail.text = movies.original_title
        binding.tvLanguageDetail.text = movies.original_language
        binding.tvReleaseDetail.text = movies.release_date

        binding.tvOverviewDetail.text = movies.overview
    }

    private fun showReview() {
        //set recycler view
        binding.rvReviewDetail.layoutManager = LinearLayoutManager(this)
        binding.rvReviewDetail.setHasFixedSize(true)
        //set adapter
        val reviewAdapter = ReviewAdapter(listReviewer)
        binding.rvReviewDetail.adapter = reviewAdapter
    }

    private fun playVideo(video: Video) {
//        val mediaController = MediaController(this)
//        binding.vvTrailerDetail.setMediaController(mediaController)
        val apiKey = "$BASE_URL_YOUTUBE"
//        val uid = Uri.parse(path)
//        binding.vvTrailerDetail.setVideoURI(uid)
//        binding.vvTrailerDetail.start()
        // Get reference to the view of Video player
        // Get reference to the view of Video player

        binding.vvTrailerDetail.initialize(
            apiKey,
            object : YouTubePlayer.OnInitializedListener {
                // Implement two methods by clicking on red
                // error bulb inside onInitializationSuccess
                // method add the video link or the playlist
                // link that you want to play In here we
                // also handle the play and pause
                // functionality
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    youTubePlayer.loadVideo(video.key)
                    youTubePlayer.play()
                }

                // Inside onInitializationFailure
                // implement the failure functionality
                // Here we will show toast
                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Toast.makeText(applicationContext, "Video player Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
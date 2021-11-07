package com.example.movies.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movies.databinding.ActivityDetailBinding
import com.example.movies.model.Movies

class DetailActivity : AppCompatActivity() {

    companion object {
        private val TAG = DetailActivity::class.java.simpleName
        const val EXTRA_MOVIES = "extra_movies"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //set movie
        val movie = intent.getParcelableExtra<Movies>(EXTRA_MOVIES)!!

        //setData()
        setData(movie)
    }

    private fun setData(movies: Movies) {
        Glide.with(this).load(movies.backdrop_path).centerCrop().into(binding.ivMoviesDetail)
        Glide.with(this).load(movies.poster_path).centerCrop().into(binding.ivMoviesPosterDetail)

        binding.tvTitleDetail.text = movies.original_title
        binding.tvLanguageDetail.text = movies.original_language
        binding.tvReleaseDetail.text = movies.release_date

        binding.tvOverviewDetail.text = movies.overview
    }

    private fun showReview() {
//        //set recycler view
//        binding.rvReviewDetail.layoutManager = GridLayoutManager(this, 2)
//        binding.rvReviewDetail.setHasFixedSize(true)
//        //set adapter
//        val reviewAdapter = MoviesAdapter(listReview)
//        binding.rvReviewDetail.adapter = reviewAdapter
    }
}
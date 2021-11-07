package com.example.movies.movies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapter.MoviesAdapter
import com.example.movies.databinding.ActivityMoviesBinding
import com.example.movies.model.Genres
import com.example.movies.model.Movies

class MoviesActivity : AppCompatActivity() {

    companion object {
        private val TAG = MoviesActivity::class.java.simpleName
        const val EXTRA_GENRES = "extra_genres"
    }

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private var listMovies = ArrayList<Movies>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //set genre id
        val genre = intent.getParcelableExtra<Genres>(EXTRA_GENRES)

        //set toolbar
        binding.tlMovies.title = genre?.name

        //set view model
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MoviesViewModel::class.java)

        //call set genres
        genre?.let { moviesViewModel.seMovies(it.id) }

        //call get genres
        moviesViewModel.getMovies().observe(this, Observer { listMovies ->
            Log.i(TAG, "call getMovies...")
            //check list genres
            if (listMovies != null) {
                Log.i(TAG, "info Movies: $listMovies")
                this.listMovies = listMovies
                //show recycler view
                showRecyclerView()
                Log.i(TAG, "call Movies Success")
            } else {

            }
        })

    }

    private fun showRecyclerView() {
        //set recycler view
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)
        binding.rvMovies.setHasFixedSize(true)
        //set adapter
        val moviesAdapter = MoviesAdapter(listMovies)
        binding.rvMovies.adapter = moviesAdapter
    }
}
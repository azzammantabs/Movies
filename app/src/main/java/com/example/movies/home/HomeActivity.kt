package com.example.movies.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapter.GenresAdapter
import com.example.movies.databinding.ActivityHomeBinding
import com.example.movies.model.Genres

class HomeActivity : AppCompatActivity() {

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var listGenres = ArrayList<Genres>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //set toolbar
        binding.tlHome.title = getString(R.string.app_name)

        //set view model
        homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)

        //call set genres
        homeViewModel.setGenres()

        //call get genres
        homeViewModel.getGenres().observe(this, Observer { listGenres ->
            Log.i(TAG, "call getGenres...")
            //check list genres
            if (listGenres != null) {
                Log.i(TAG, "info Genres: $listGenres")
                this.listGenres = listGenres
                //show recycler view
                showRecyclerView()
                Log.i(TAG, "call genre Success")
            } else {

            }
        })

    }

    private fun showRecyclerView() {
        //set recycler view
        binding.rvGenres.layoutManager = GridLayoutManager(this, 3)
        binding.rvGenres.setHasFixedSize(true)
        //set adapter
        val genresAdapter = GenresAdapter(listGenres)
        binding.rvGenres.adapter = genresAdapter
    }
}
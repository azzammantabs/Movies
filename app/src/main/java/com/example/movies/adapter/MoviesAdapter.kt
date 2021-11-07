package com.example.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ListMoviesBinding
import com.example.movies.detail.DetailActivity
import com.example.movies.helper.Constant.Companion.BASE_URL_IMAGE
import com.example.movies.model.Movies

class MoviesAdapter(private val listMovies: ArrayList<Movies>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var _binding: ListMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        _binding = ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = listMovies.size

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: Movies) {
            with(itemView) {
                //set image movies
                Glide.with(itemView.context)
                    .load("${BASE_URL_IMAGE}/${movies.poster_path}")
                    .error(R.drawable.ic_outline_image_24)
                    .into(binding.ivMoviesList)
                itemView.setOnClickListener {
                    val detailIntent = Intent(itemView.context, DetailActivity::class.java)
                    detailIntent.putExtra(DetailActivity.EXTRA_MOVIES, movies)
                    itemView.context.startActivity(detailIntent)
                }
            }
        }
    }
}
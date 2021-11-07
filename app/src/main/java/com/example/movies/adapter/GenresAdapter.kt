package com.example.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ListGenresBinding
import com.example.movies.model.Genres
import com.example.movies.movies.MoviesActivity

class GenresAdapter(private val listGenres: ArrayList<Genres>) :
    RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private var _binding: ListGenresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresAdapter.GenresViewHolder {
        _binding = ListGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GenresAdapter.GenresViewHolder, position: Int) {
        holder.bind(listGenres[position])
    }

    override fun getItemCount(): Int = listGenres.size

    inner class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(genres: Genres) {
            with(itemView) {
                //set genre name
                binding.tvGenresList.text = genres.name
                itemView.setOnClickListener {
                    //move to Movies Intent
                    val moviesIntent = Intent(itemView.context, MoviesActivity::class.java)
                    moviesIntent.putExtra(MoviesActivity.EXTRA_ID_GENRES, genres.id)
                    itemView.context.startActivity(moviesIntent)
                }
            }
        }
    }


}
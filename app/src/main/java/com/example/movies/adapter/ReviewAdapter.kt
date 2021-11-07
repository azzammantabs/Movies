package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ListReviewBinding
import com.example.movies.model.Reviewer

class ReviewAdapter(private val listReview: ArrayList<Reviewer>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var _binding: ListReviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewAdapter.ReviewViewHolder {
        _binding = ListReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        holder.bind(listReview[position])
    }

    override fun getItemCount(): Int = listReview.size

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(reviewer: Reviewer) {
            with(itemView) {
                //set avatar reviewer
                Glide.with(itemView.context)
                    .load(reviewer.avatar_path)
                    .error(R.drawable.ic_baseline_person_outline_24)
                    .into(binding.ivAvatarReview)
                //set name reviewer
                binding.tvAuthorReview.text = reviewer.author
                binding.tvContentReview.text = reviewer.content
                itemView.setOnClickListener {
                    //
                }
            }
        }
    }


}
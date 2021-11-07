package com.example.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reviewer(
    var id: String,
    var author: String,
    var avatar_path: String,
    var content: String
) : Parcelable
package com.example.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    var id: String,
    var name: String,
    var key: String,
    var site: String
) : Parcelable
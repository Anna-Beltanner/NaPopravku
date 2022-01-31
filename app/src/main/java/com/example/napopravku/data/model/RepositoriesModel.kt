package com.example.napopravku.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoriesModel(
    val name: String,
    val owner: Owner,
    val id: Int,
    val full_name: String
) : Parcelable

@Parcelize
data class Owner(
    val login: String,
    val avatar_url: String
) : Parcelable




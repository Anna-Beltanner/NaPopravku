package com.example.napopravku.data.model


data class RepositoriesModel(

    val owner: Owner,

    val id: Int,

    val full_name: String,


    )

data class Owner(
    val login: String,
    val avatar_url: String
)




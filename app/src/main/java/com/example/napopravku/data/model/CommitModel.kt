package com.example.napopravku.data.model


data class CommitModel(
    val commit: DetailCommitModel,
    val parents: List<ParentsModel>
)

data class DetailCommitModel(
    val message: String,
    val author: AuthorModel

)


data class AuthorModel(
    val name: String,
    val email: String,
    val date: String
)

data class ParentsModel(
    val url: String,
    val sha: String

)


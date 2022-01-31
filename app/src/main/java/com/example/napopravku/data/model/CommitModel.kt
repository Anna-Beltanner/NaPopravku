package com.example.napopravku.data.model

/**
 * Created on 31.01.2022 by Andrey Voloshin.
 */
data class CommitModel(
    val commit: DetailCommitModel
)

data class DetailCommitModel(
    val message: String
)
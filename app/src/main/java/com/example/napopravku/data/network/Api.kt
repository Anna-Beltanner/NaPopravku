package com.example.napopravku.data.network

import com.example.napopravku.data.model.CommitModel
import com.example.napopravku.data.model.RepositoriesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

   //с помощью аннотаций описываем запросы


   @GET("/repositories")
   suspend fun getDataFromGithubApi (@Query("since")since: Int? = null): List<RepositoriesModel>


   @GET("/repos/{userName}/{repositoryName}/commits")
   suspend fun getDataLastCommit(@Path("userName") userName: String, @Path("repositoryName") repositoryName: String): List<CommitModel>
}



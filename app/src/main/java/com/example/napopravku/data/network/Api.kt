package com.example.napopravku.data.network

import com.example.napopravku.data.model.RepositoriesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    // @GET запрос / repositories (список репозиториев)

    // @GET запрос в commits URL из репозиториес модел

  // 2 вида ответов - ок и ошибка
    // в ошибке будет exception - отсутствие интернета, обработать, отправить во вью команду, чтобы отобразил ошибку


   @GET("/repositories")
   suspend fun getDataFromGithubApi (@Query("since")since: Int? = null): List<RepositoriesModel>


}



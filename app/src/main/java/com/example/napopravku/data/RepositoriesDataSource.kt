package com.example.napopravku.data

import android.graphics.pdf.PdfDocument
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.data.network.Api
import com.example.napopravku.data.network.NetworkService

/*
class RepositoriesDataSource(var api: Api): PagingSource<Int, RepositoriesModel>() {
    override fun getRefreshKey(state: PagingState<Int, RepositoriesModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoriesModel> {
       // api.getDataFromGithubApi(params.i)
    }
}*/

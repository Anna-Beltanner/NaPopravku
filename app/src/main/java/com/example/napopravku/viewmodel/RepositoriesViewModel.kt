package com.example.napopravku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//логика запроса в лист паблик репозиториес
//livedata списка репозиторесмодел, то, что возвращается в ответ на запрос

class RepositoriesViewModel : ViewModel() {


    /*private val repository = CharactersRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(Constants.PAGE_SIZE)
        .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope, repository)
    val charactersPagedListLiveData: LiveData<PagedList<GetCharacterByIdResponse>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()*/

    private var retrofitInstance: Api? = null

    private var repositoriesModelLiveData: MutableLiveData<List<RepositoriesModel>> =
        MutableLiveData()

    private var errorLiveData: MutableLiveData<String> =
        MutableLiveData()

    fun setRetrofitInstance(api: Api) {
        retrofitInstance = api
    }

    fun getRepositoriesModelObserver(): MutableLiveData<List<RepositoriesModel>> {
        return repositoriesModelLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

    fun createApiCall(id: Int? = null) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    retrofitInstance?.getDataFromGithubApi(id)
                }
            }.onSuccess {
                repositoriesModelLiveData.postValue(it)
            }.onFailure {
                // Здесь отображаются ошибки которые возникли во время выполнения запроса, в том числе отсуствие интернета.
                errorLiveData.postValue(it.localizedMessage)
            }
        }
    }
}
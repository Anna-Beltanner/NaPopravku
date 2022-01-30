package com.example.napopravku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


    var repositoriesModelLiveData: MutableLiveData<List<RepositoriesModel>>

    init {

        //создаем объект LiveData
        repositoriesModelLiveData = MutableLiveData()

    }

    fun getRepositoriesModelObserver(): MutableLiveData<List<RepositoriesModel>> {
        return repositoriesModelLiveData

    }

    fun createApiCall(retrofitInstance: Api, id: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {


            //val response = listOf<RepositoriesModel>(RepositoriesModel(id = 1, "blalbabla", "fdf", "dfdf"), RepositoriesModel(2, "blblblblalb", "dfd", "fdfdf"))

            val response = retrofitInstance.getDataFromGithubApi(id)
            repositoriesModelLiveData.postValue(response)


        }
    }


}
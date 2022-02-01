package com.example.napopravku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//логика запроса в список репозиториев
//livedata списка RepositoriesModel, то, что возвращается в ответ на запрос

class RepositoriesViewModel : ViewModel() {


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
                // Здесь отображаются ошибки, которые возникли во время выполнения запроса, в том числе отсуствие интернета.
                errorLiveData.postValue(it.localizedMessage)
            }
        }
    }
}
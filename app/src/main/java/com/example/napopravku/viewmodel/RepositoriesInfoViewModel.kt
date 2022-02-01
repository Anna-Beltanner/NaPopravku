package com.example.napopravku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.napopravku.data.model.CommitModel
import com.example.napopravku.data.model.DetailCommitModel
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoriesInfoViewModel: ViewModel() {

    private var retrofitInstance: Api? = null

    private var lastCommitLiveData: MutableLiveData<CommitModel> =
        MutableLiveData()

    private var errorLiveData: MutableLiveData<String> =
        MutableLiveData()

    fun setRetrofitInstance(api: Api) {
        retrofitInstance = api
    }

    fun getLastCommitLiveData(): MutableLiveData<CommitModel> {
        return lastCommitLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

    fun loadLastCommit(userName: String, repositoryName: String) {
        viewModelScope.launch {
            //разделяем ответ на 2 части
            runCatching {
                withContext(Dispatchers.IO) {
                    retrofitInstance?.getDataLastCommit(userName, repositoryName)
                }
            }.onSuccess {
                // Нужно взять первый коммит из списка. В нем есть параметр с детальной информацией по коммиту это commit; его передаем в LiveData

                lastCommitLiveData.postValue(it?.first())

            }.onFailure {
                // Здесь отображаются ошибки, которые возникли во время выполнения запроса, в том числе - отсуствие интернета.

                errorLiveData.postValue(it.localizedMessage)
            }
        }
    }
}


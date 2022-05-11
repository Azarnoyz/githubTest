package com.azarenko.gitrepostest.ui.fragments.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azarenko.gitrepostest.data.RepositoriesData
import com.azarenko.gitrepostest.db.ReposDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor
    (private val dataBaseRepository: ReposDbRepository) : ViewModel() {


    private val _reposListDataFromDB = MutableLiveData<List<RepositoriesData>>()

    val reposListDataFromDB: LiveData<List<RepositoriesData>>
        get() = _reposListDataFromDB


    fun getReposListsFromDb() = viewModelScope.launch {
        dataBaseRepository.getAll().collect {
            _reposListDataFromDB.postValue(it)
        }
    }

}
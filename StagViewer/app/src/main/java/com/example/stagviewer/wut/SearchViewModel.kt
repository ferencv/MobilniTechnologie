package com.example.stagviewer.wut

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.stagviewer.Database.getDatabase
import com.example.stagviewer.repository.StagProgramRepository
import kotlinx.coroutines.*
import java.io.IOException


class SearchViewModel(application: Application, filter: ProgramsFilter?) : AndroidViewModel(application) {

    // The internal MutableLiveData String that stores the most recent response
    private val programRepository = StagProgramRepository(getDatabase(application))
    var _searchName = MutableLiveData<String>(filter?.searchString?:"")
    var searchName: String?
        get() = _searchName.value
        set(value) { _searchName.value = value }

    fun searchNameChanged(text: String?)
    {
        resultString.value = "Hledáme"
        searchName = text
    }

    val programs = Transformations.switchMap(_searchName){
        val items : LiveData<List<StagProgramModel>> = programRepository.filterPrograms("%"+it+"%")
        items
    }

    fun updateResultString(count: Int)
    {
        resultString.value = "Pro výraz '"+_searchName.value+"' bylo nalezeno " +count.toString() + " záznamů."
    }

    var resultString = MutableLiveData<String>("")
//    var resultString: String?
//        get() = _resultString.value
//        set(value) { _resultString.value = value }


    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                programRepository.refreshPrograms()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(programs.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}

class SearchViewModelFactory(val app: Application, val filter: ProgramsFilter?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(app, filter) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
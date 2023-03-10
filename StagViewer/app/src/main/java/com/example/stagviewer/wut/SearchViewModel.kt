package com.example.stagviewer.wut

import android.app.Application
import androidx.lifecycle.*
import com.example.stagviewer.Database.getFacultiesDatabase
import com.example.stagviewer.Database.getProgramsDatabase
import com.example.stagviewer.repository.StagFacultyRepository
import com.example.stagviewer.repository.StagProgramRepository
import kotlinx.coroutines.*
import java.io.IOException


class SearchViewModel(application: Application, filter: ProgramsFilter?) : AndroidViewModel(application) {

    // The internal MutableLiveData String that stores the most recent response
    private val programRepository = StagProgramRepository(getProgramsDatabase(application))

    var searchString = MutableLiveData<String>(filter?.searchString?:"")
    var facultyId = MutableLiveData<String>(filter?.facultyId?:"")
    var facultyName = MutableLiveData<String>(filter?.facultyName?:"")
    var facultyResultString = MutableLiveData<String>(filter?.facultyName ?: "")

    val programs = Transformations.switchMap(searchString){
        getProgramsAsync(facultyId.value, it)
    }

    fun getProgramsAsync(facultyId:String?, term: String) = liveData(Dispatchers.IO) {
        emitSource(programRepository.filterPrograms(facultyId?:"","%" + term + "%"))
    }



    fun updateResultString(count: Int)
    {
        resultString.value = " Pro výraz '"+searchString.value+"' bylo nalezeno " +count.toString() + " záznamů."
    }

    var resultString = MutableLiveData<String>("")


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
                //if(programs.value.isNullOrEmpty())
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
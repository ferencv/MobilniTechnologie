package com.example.stagviewer.wut

import android.app.Application
import androidx.lifecycle.*
import com.example.stagviewer.Database.getFacultiesDatabase
import com.example.stagviewer.Database.getProgramsDatabase
import com.example.stagviewer.repository.StagFacultyRepository
import com.example.stagviewer.repository.StagProgramRepository
import kotlinx.coroutines.*
import java.io.IOException


class FacultySelectViewModel(application: Application, filter: ProgramsFilter?) : AndroidViewModel(application) {

    private val facultyRepository = StagFacultyRepository(getFacultiesDatabase(application))
    val faculties = facultyRepository.getFaculties()

    var searchString = MutableLiveData<String>(filter?.searchString?:"")
    var facultyId = MutableLiveData<String>(filter?.facultyId?:"")


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
                facultyRepository.refreshFaculties()
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

class FacultySelectViewModelFactory(val app: Application, val filter: ProgramsFilter?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FacultySelectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FacultySelectViewModel(app, filter) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
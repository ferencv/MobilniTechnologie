package com.example.stagviewer.Detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagviewer.wut.ProgramsFilter
import com.example.stagviewer.wut.StagProgramModel
import kotlinx.coroutines.*


class DetailViewModel(application: Application, program: StagProgramModel, filter: ProgramsFilter) : AndroidViewModel(application) {

    var program = program
    var filter = filter
}

class DetailViewModelFactory(val app: Application, val program: StagProgramModel, val filter: ProgramsFilter) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(app, program, filter) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
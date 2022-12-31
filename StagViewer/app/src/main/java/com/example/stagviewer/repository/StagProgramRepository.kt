package com.example.stagviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.stagviewer.Database.StagProgram
import com.example.stagviewer.Database.StagProgramsDatabase
import com.example.stagviewer.Database.toProgramModels
import com.example.stagviewer.wut.StagProgramModel
import com.example.stagviewer.network.StagApi
import com.example.stagviewer.network.toPrograms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StagProgramRepository(private val database: StagProgramsDatabase) {

    fun getAllPrograms(): LiveData<List<StagProgramModel>> =
        Transformations.map(database.stagProgramDao.getStagPrograms()) {
            it.toProgramModels()
    }

    fun filterPrograms(searchName: String?): LiveData<List<StagProgramModel>> =
        Transformations.map(database.stagProgramDao.filterPrograms(searchName)) {
            it.toProgramModels()
        }


    suspend fun refreshPrograms() {
        withContext(Dispatchers.IO) {
            val response = StagApi.stagService.getProgrammes()
            database.stagProgramDao.insertAll(response.programInfo.toPrograms())
        }
    }
}
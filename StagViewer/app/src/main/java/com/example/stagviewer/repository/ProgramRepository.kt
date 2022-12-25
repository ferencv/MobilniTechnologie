package com.example.stagviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.stagviewer.Database.ProgramsDatabase
import com.example.stagviewer.Database.toProgramModels
import com.example.stagviewer.wut.ProgramModel
import com.example.stagviewer.network.StagApi
import com.example.stagviewer.network.toPrograms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgramRepository(private val database: ProgramsDatabase) {

    val programs: LiveData<List<ProgramModel>> = Transformations.map(database.programDao.getPrograms())
    {
        it.toProgramModels()
    }

    suspend fun refreshPrograms() {
        withContext(Dispatchers.IO) {
            val response = StagApi.stagService.getProgrammes()
            database.programDao.insertAll(response.fields.toPrograms())
        }
    }
}
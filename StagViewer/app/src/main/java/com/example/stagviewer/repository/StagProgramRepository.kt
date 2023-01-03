package com.example.stagviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.stagviewer.Database.*
import com.example.stagviewer.network.StagApiProgram
import com.example.stagviewer.wut.StagProgramModel
import com.example.stagviewer.network.toPrograms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StagProgramRepository(private val database: StagProgramsDatabase) {

    fun getAllPrograms(): LiveData<List<StagProgramModel>> =
        Transformations.map(database.stagProgramDao.getStagPrograms()) {
            it.toProgramModels()
    }

    fun filterPrograms(facultyId: String?, searchName: String?): LiveData<List<StagProgramModel>> =
        Transformations.map(database.stagProgramDao.filterPrograms(facultyId, searchName)) {
            it.toProgramModels()
        }


    suspend fun refreshPrograms() {
        withContext(Dispatchers.IO) {
            val response = StagApiProgram.stagService.getProgrammes()
            database.stagProgramDao.insertAll(response.programInfo.toPrograms())
        }
    }
}
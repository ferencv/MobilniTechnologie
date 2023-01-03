package com.example.stagviewer.repository

import androidx.lifecycle.Transformations
import com.example.stagviewer.Database.*
import com.example.stagviewer.network.StagApiFaculty
import com.example.stagviewer.network.toFaculties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StagFacultyRepository(private val database: StagFacultiesDatabase) {

    val facultyLevel = 2

    fun getFaculties() =
        Transformations.map(database.stagFacultyDao.getFaculties()) {
            it.toFacultyModels()
    }


    suspend fun refreshFaculties() {
        withContext(Dispatchers.IO) {
            val response = StagApiFaculty.stagService.getFaculties()
            database.stagFacultyDao.insertAll(response.pracoviste.filter{it.level == facultyLevel}.toFaculties())
        }
    }
}
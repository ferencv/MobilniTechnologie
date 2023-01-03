package com.example.stagviewer.Database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StagFacultyDao {

    @Query("select * from StagFaculty")
    fun getFaculties(): LiveData<List<StagFaculty>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(faculties: List<StagFaculty>)
}

@Database(entities = [StagFaculty::class], version = 1)
abstract class StagFacultiesDatabase: RoomDatabase() {
    abstract val stagFacultyDao: StagFacultyDao
}

private lateinit var INSTANCE: StagFacultiesDatabase

fun getFacultiesDatabase(context: Context): StagFacultiesDatabase {
    synchronized(StagFacultiesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                StagFacultiesDatabase::class.java,
                "StagFaculties").build()
        }
    }
    return INSTANCE
}
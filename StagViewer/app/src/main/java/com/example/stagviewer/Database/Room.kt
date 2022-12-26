package com.example.stagviewer.Database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StagProgramDao {

    @Query("select * from StagProgram")
    fun getStagPrograms(): LiveData<List<StagProgram>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(programs: List<StagProgram>)
}

@Database(entities = [StagProgram::class], version = 1)
abstract class StagProgramsDatabase: RoomDatabase() {
    abstract val stagProgramDao: StagProgramDao
}

private lateinit var INSTANCE: StagProgramsDatabase

fun getDatabase(context: Context): StagProgramsDatabase {
    synchronized(StagProgramsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                StagProgramsDatabase::class.java,
                "StagPrograms").build()
        }
    }
    return INSTANCE
}
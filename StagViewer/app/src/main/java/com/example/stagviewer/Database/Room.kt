package com.example.stagviewer.Database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProgramDao {

    @Query("select * from ProgramsDatabase")
    fun getPrograms(): LiveData<List<Program>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( programs: List<Program>)
}

@Database(entities = [ProgramsDatabase::class], version = 1)
abstract class ProgramsDatabase: RoomDatabase() {
    abstract val programDao: ProgramDao
}

private lateinit var INSTANCE: ProgramsDatabase

fun getDatabase(context: Context): ProgramsDatabase {
    synchronized(ProgramsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ProgramsDatabase::class.java,
                "Programs").build()
        }
    }
    return INSTANCE
}
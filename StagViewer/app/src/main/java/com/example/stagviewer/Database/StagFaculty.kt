package com.example.stagviewer.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stagviewer.wut.StagFacultyModel
import com.example.stagviewer.wut.StagProgramModel

@Entity
class StagFaculty constructor(
    @PrimaryKey
    var abbrev: String = "",
    var name: String = "",
    var level: Int = 0)
{
}

fun List<StagFaculty>.toFacultyModels(): List<StagFacultyModel> {
    return map {
        StagFacultyModel(
            level = it.level,
            abbrev = it.abbrev,
            name = it.name,
            )
    }
}
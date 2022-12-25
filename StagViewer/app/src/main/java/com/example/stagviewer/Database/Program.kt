package com.example.stagviewer.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stagviewer.wut.ProgramModel

@Entity
class Program constructor(
    @PrimaryKey
    var id: Int = 0,
    //var combId: Int = 0,
    //var studyProgramId: Int = 0,
    var name: String = "",
    var facultyShort: String = "",
    var faculty: String = "",
    var formName: String = "",
    var formShort: String = "",
    var type: String = "",
    var typeShort: String = "",
    var language: String = "",
    var languageShort: String = "",
    var year: Int = 0,
    //var oneField: Boolean = false,
    //var onlineAppForm: Boolean = false,
    var onlineAppFormDeadline: String? = "",
    var displayFrom: String? = "")
{
}

fun List<Program>.toProgramModels(): List<ProgramModel> {
    return map {
        ProgramModel(
            id = it.id,
            //combId = it.combId,
            //studyProgramId = it.studyProgramId,
            name = it.name,
            facultyShort = it.facultyShort,
            faculty = it.faculty,
            formName = it.formName,
            type = it.type,
            typeShort = it.typeShort,
            language = it.language,
            languageShort = it.languageShort,
            year = it.year.toString(),
            //oneField = it.oneField,
            //onlineAppForm = it.onlineAppForm,
            onlineAppFormDeadline = it.onlineAppFormDeadline,
            displayFrom = it.displayFrom
            )
    }
}
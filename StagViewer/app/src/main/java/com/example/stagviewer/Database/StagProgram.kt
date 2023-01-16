package com.example.stagviewer.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stagviewer.wut.StagProgramModel

@Entity
class StagProgram constructor(
    @PrimaryKey
    var stprIdno: Int = 0,
    var name: String = "",
    var nameCz: String = "",
    var nameEn: String = "",
    var code: String = "",
    var title: String = "",
    var titleShort: String = "",
    var form: String = "",
    var faculty: String = "",
    var stdLength: Double = 0.0,
    var maxLength: Double = 0.0,
    var garant: String = "",
    var lang: String = "",
    var isced: String = "",
    var requirementsCz: String = "",
    var requirementsEn: String = "")
{
}

fun List<StagProgram>.toProgramModels(): List<StagProgramModel> {
    return map {
        StagProgramModel(
            stprIdno = it.stprIdno,
            name = it.name,
            nameCz = it.nameCz,
            nameEn = it.nameEn,
            code = it.code,
            title = it.title,
            titleShort = it.titleShort,
            form = it.form,
            faculty = it.faculty,
            stdLength = it.stdLength.toString(),
            maxLength = it.maxLength.toString(),
            garant = it.garant,
            lang = it.lang,
            isced = it.isced,
            requirementsCz = it.requirementsCz,
            requirementsEn = it.requirementsEn,
            )
    }
}
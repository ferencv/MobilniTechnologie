package com.example.stagviewer.wut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StagProgramModel(
    var stprIdno: Int = 0,
    var name: String = "",
    var nameCz: String = "",
    var nameEn: String = "",
    var code: String = "",
    var title: String = "",
    var titleShort: String = "",
    var form: String = "",
    var faculty: String = "",
    var stdLength: String = "",
    var maxLength: String = "",
    var garant: String = "",
    var lang: String = "",
    var isced: String = "",
    var requirementsCz: String = "",
    var requirementsEn: String = ""):Parcelable
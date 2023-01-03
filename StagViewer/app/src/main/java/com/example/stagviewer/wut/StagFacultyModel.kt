package com.example.stagviewer.wut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StagFacultyModel(
    var level: Int = 0,
    var abbrev: String = "",
    var name: String = ""):Parcelable
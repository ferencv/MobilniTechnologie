package com.example.stagviewer.wut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProgramsFilter(
    var searchString: String = "",
):Parcelable
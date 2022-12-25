package com.example.stagviewer.network

data class StagProgramsResponse(
    var fields: List<StagProgram> = listOf(),
    var count: Int = 0,
    var total: Int = 0,
    var faculty: String? = "",
    )
{

}
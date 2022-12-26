package com.example.stagviewer.network

data class CataloqueProgramsResponse(
    var fields: List<StagProgramDTO> = listOf(),
    var count: Int = 0,
    var total: Int = 0,
    var faculty: String? = "",
    )
{

}
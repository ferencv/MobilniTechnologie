package com.example.stagviewer.network

import com.example.stagviewer.Database.Program
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



interface StagService {
    @GET("GetFields")
    suspend fun getProgrammes(): StagProgramsResponse
}

fun List<StagProgram>.toPrograms(): List<Program> {
    return map {
        Program(
            id = it.id,
            //combId = it.combId,
            //studyProgramId = it.studyProgramId,
            name = it.name?: "",
            facultyShort = it.facultyShort?: "",
            faculty = it.faculty ?: "",
            formName = it.formName?: "",
            type = it.type?: "",
            typeShort = it.typeShort?: "",
            language = it.language?: "",
            languageShort = it.languageShort?: "",
            year = it.year,
            //oneField = it.oneField,
            //onlineAppForm = it.onlineAppForm,
            onlineAppFormDeadline = it.onlineAppFormDeadline ?: "",
            displayFrom = it.displayFrom ?: ""
        )
    }
}

object StagApi {
    private const val BASE_URL =
        "https://studium.upol.cz/Catalog/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val stagService = retrofit.create(StagService::class.java)

}
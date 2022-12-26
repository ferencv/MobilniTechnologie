package com.example.stagviewer.network

import com.example.stagviewer.Database.CataloqueProgram
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



interface CataloqueService {
    @GET("GetFields")
    suspend fun getProgrammes(): CataloqueProgramsResponse
}

fun List<CataloqueProgramDTO>.toPrograms(): List<CataloqueProgram> {
    return map {
        CataloqueProgram(
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
//f96ab547e3615608448d411cd38e3423
object CataloqueApi {
    private const val BASE_URL =
        "https://studium.upol.cz/Catalog/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val cataloqueService = retrofit.create(CataloqueService::class.java)

}
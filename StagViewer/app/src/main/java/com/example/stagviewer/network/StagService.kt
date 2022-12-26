package com.example.stagviewer.network

import com.example.stagviewer.Database.StagProgram
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



interface StagService {
    @GET("getStudijniProgramy?kod=%25&forma=K&outputFormat=JSON&rok=2022")
    suspend fun getProgrammes(): StagProgramsResponse
}

fun List<StagProgramDTO>.toPrograms(): List<StagProgram> {
    return map {
        StagProgram(
            stprIdno = it.stprIdno,
            name = it.nazev ?: "",
            nameCz = it.nazevCz  ?: "",
            nameEn = it.nazevAn  ?: "",
            code = it.kod ?: "",
            title = it.titul ?: "",
            titleShort = it.titulZkr ?: "",
            form = it.forma ?: "",
            faculty = it.fakulta ?: "",
            stdLength = it.stdDelka,
            maxLength = it.maxDelka,
            garant = it.garant  ?: "",
            lang = it.jazyk  ?: "",
            isced = it.kodIsced  ?: "",
            requirementsCz = it.pozadavkyNaPrijetiCz  ?: "",
            requirementsEn = it.pozadavkyNaPrijetiAn ?: "",
        )
    }
}

object StagApi {
    private const val BASE_URL =
        "https://stagservices.upol.cz/ws/services/rest2/programy/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val stagService = retrofit.create(StagService::class.java)

}
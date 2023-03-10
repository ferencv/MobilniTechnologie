package com.example.stagviewer.network

import com.example.stagviewer.Database.StagProgram
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



interface StagServiceProgram {
    @GET("programy/getStudijniProgramy?kod=%25&forma=K&outputFormat=JSON&rok=2022")
    //programy/getStudijniProgramy?kod=%25&fakulta=FAI&outputFormat=XLSX
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
object StagApiProgram {
    private const val BASE_URL =
        "https://stag-ws.utb.cz/ws/services/rest2/"
    //"https://stag-ws.utb.cz/ws/services/rest2/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val stagService = retrofit.create(StagServiceProgram::class.java)

}
package com.example.stagviewer.network

import com.example.stagviewer.Database.StagFaculty
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



interface StagServiceFaculty {
    @GET("ciselniky/getHierarchiePracovist?outputFormat=JSON")
    suspend fun getFaculties(): StagFacultiesResponse
}

fun List<StagFacultyDTO>.toFaculties(): List<StagFaculty> {
    return map {
        StagFaculty(
            level = it.level,
            abbrev = it.zkratka,
            name = it.nazev ?: "",
        )
    }
}

object StagApiFaculty {
    private const val BASE_URL =
        "https://stag-ws.utb.cz/ws/services/rest2/"
    //https://stagservices.upol.cz/ws/services/rest2/

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val stagService = retrofit.create(StagServiceFaculty::class.java)

}
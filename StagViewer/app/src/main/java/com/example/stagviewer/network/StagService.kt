package com.example.stagviewer.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://studium.upol.cz/Catalog/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface StagService {
    @GET("GetFields")
    suspend fun getProgrammes(): StagProgramsResponse
}

object StagApi {
    val retrofitService : StagService by lazy {
        retrofit.create(StagService::class.java) }
}
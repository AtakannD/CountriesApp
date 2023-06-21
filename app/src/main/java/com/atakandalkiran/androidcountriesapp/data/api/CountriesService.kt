package com.atakandalkiran.androidcountriesapp.data.api

import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {

    @GET("all")
    suspend fun getProperties(): Response<List<CountryInfos>>

    @GET("name/{id}")
    suspend fun searchData(
        @Path("id") searchKeyword : String?
    ): Response<List<CountryInfos>>
}
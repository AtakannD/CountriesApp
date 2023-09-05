package com.atakandalkiran.androidcountriesapp.data.remote

import com.atakandalkiran.androidcountriesapp.data.api.CountriesService
import retrofit2.Response
import com.atakandalkiran.androidcountriesapp.data.Result
import java.io.IOException
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(
    private val countriesService: CountriesService
) {
    suspend fun searchCountry(searchKeyword : String) =  getResult {
        countriesService.searchData(
            searchKeyword = searchKeyword
        )
    }

    suspend fun getCountryProperty() = getResult {
        countriesService.getProperties()
    }

    private suspend fun <T> getResult(
        serviceCall: suspend () -> Response<T>
    ): Result<T> {
        try {
            val response = serviceCall()
            if (response.isSuccessful) {
                response.body().takeIf { it != null }?.let { responseBody ->
                    return Result.Success(responseBody)
                }
            }
            return Result.Failure(IOException("${response.code()} ${response.message()}"))
        } catch (e: Exception) {
            return Result.Failure(e)
        }
    }
}
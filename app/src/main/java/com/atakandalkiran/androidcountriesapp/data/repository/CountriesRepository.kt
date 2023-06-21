package com.atakandalkiran.androidcountriesapp.data.repository

import com.atakandalkiran.androidcountriesapp.data.local.CountriesDao
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import com.atakandalkiran.androidcountriesapp.data.remote.CountriesRemoteDataSource
import com.atakandalkiran.androidcountriesapp.data.Result
import com.atakandalkiran.androidcountriesapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val remoteDataSource: CountriesRemoteDataSource,
    private val localDataSource: CountriesDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    fun getCountryProperties() = flow {
        emit(remoteDataSource.getCountryProperty())
    }.flowOn(dispatcher)

    fun searchCountries(searchKeyword: String) = flow {
        val serviceResponse = remoteDataSource.searchCountry(searchKeyword)
        val isExist = localDataSource.isSearchCountryExist(searchKeyword)
        if (isExist && serviceResponse is Result.Success) {
            serviceResponse.data.find {
                it.name.common.contains(searchKeyword)
            }?.isFavorite = true
        }
        emit(serviceResponse)
    }.flowOn(dispatcher)

    fun getLocalCountryInformations() = localDataSource
        .getLocalCountryInformation()
        .flowOn(dispatcher)

    fun getSearchedCountries() = localDataSource
        .getSearchHistoryCountries()
        .flowOn(dispatcher)

    suspend fun deleteSavedCountries(countryInfos: CountryInfos) =
        withContext(dispatcher) {
            localDataSource.deleteSavedCountry(countryInfos)
        }

    suspend fun saveSearchKeyword(searchModel: SearchHistoryModel) =
        localDataSource.insert(searchModel)

    suspend fun deleteAllSavedCountries() = withContext(dispatcher) {
        localDataSource.deleteAll()
    }

    suspend fun clearSearchHistory() = withContext(dispatcher) {
        localDataSource.clearSearchHistory()
    }

    suspend fun insertOrReplaceItem(countryInfos: CountryInfos) = withContext(dispatcher) {
        localDataSource.insert(countryInfos)
    }

    suspend fun clearSearchHistoryCountries(searchedKeyword: SearchHistoryModel) = withContext(dispatcher) {
        localDataSource.clearSearchHistoryCountry(searchedKeyword)
    }
}
package com.atakandalkiran.androidcountriesapp.data.usecases

import com.atakandalkiran.androidcountriesapp.data.Result
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {
    fun getSearchedCountries(): Flow<List<SearchHistoryModel>> {
        return countriesRepository.getSearchedCountries()
    }

    suspend fun saveSearchKeyword(searchModel: SearchHistoryModel) {
        return countriesRepository.saveSearchKeyword(searchModel)
    }

    fun searchCountries(searchKeyword: String): Flow<Result<List<CountryInfos>>> {
        return countriesRepository.searchCountries(searchKeyword)
    }

    suspend fun insertOrReplaceItem(countryInfos: CountryInfos) {
        return countriesRepository.insertOrReplaceItem(countryInfos)
    }

    suspend fun deleteSavedCountries(countryInfos: CountryInfos) {
        return countriesRepository.deleteSavedCountries(countryInfos)
    }

    suspend fun clearSearchHistoryCountries(searchKeyword: SearchHistoryModel) {
        return countriesRepository.clearSearchHistoryCountries(searchKeyword)
    }
}
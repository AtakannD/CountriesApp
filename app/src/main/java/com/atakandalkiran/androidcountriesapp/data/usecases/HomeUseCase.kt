package com.atakandalkiran.androidcountriesapp.data.usecases

import com.atakandalkiran.androidcountriesapp.data.Result
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {
    fun getCountryProperties() : Flow<Result<List<CountryInfos>>> {
        return countriesRepository.getCountryProperties()
    }

    suspend fun insertOrReplaceItem(countryInfos: CountryInfos) {
        return countriesRepository.insertOrReplaceItem(countryInfos)
    }

    suspend fun deleteSavedCountries(countryInfos: CountryInfos) {
        return countriesRepository.deleteSavedCountries(countryInfos)
    }
}
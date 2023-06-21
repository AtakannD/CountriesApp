package com.atakandalkiran.androidcountriesapp.data.usecases

import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {
    fun getLocalCountryInformations(): Flow<List<CountryInfos>> {
        return countriesRepository.getLocalCountryInformations()
    }

    suspend fun deleteSavedCountries(countryInfos: CountryInfos) {
        return countriesRepository.deleteSavedCountries(countryInfos)
    }
}
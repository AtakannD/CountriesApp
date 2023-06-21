package com.atakandalkiran.androidcountriesapp.data.usecases

import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import javax.inject.Inject

class SavedSettingsUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
){
    suspend fun deleteAllSavedCountries() {
        return countriesRepository.deleteAllSavedCountries()
    }
}
package com.atakandalkiran.androidcountriesapp.data.usecases

import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import javax.inject.Inject

class SearchSettingsUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {
    suspend fun clearSearchHistory() {
        return countriesRepository.clearSearchHistory()
    }
}
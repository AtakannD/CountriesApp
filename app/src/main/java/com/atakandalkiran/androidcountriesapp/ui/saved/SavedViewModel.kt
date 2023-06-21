package com.atakandalkiran.androidcountriesapp.ui.saved

import androidx.lifecycle.ViewModel
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.usecases.SavedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val savedUseCase: SavedUseCase
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _countries = MutableStateFlow<List<CountryInfos?>>(emptyList())
    val countries = _countries.asStateFlow()

    init {
        getSavedCountriesData()
    }

    private fun getSavedCountriesData() {
        viewModelScope.launch {
            savedUseCase.getLocalCountryInformations().collectLatest { result ->
                        saveCountries(result)
                }
            }
        }

    private fun saveCountries(countryInfos: List<CountryInfos>) {
        _countries.value = countryInfos
    }

    fun clearSavedCountries(countryInfos: CountryInfos) {
        viewModelScope.launch {
            savedUseCase.deleteSavedCountries(countryInfos)
        }
    }
}
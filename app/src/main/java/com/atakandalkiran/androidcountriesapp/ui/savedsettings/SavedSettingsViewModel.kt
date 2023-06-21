package com.atakandalkiran.androidcountriesapp.ui.savedsettings

import androidx.lifecycle.ViewModel
import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import com.atakandalkiran.androidcountriesapp.data.usecases.SavedSettingsUseCase
import com.atakandalkiran.androidcountriesapp.data.usecases.SavedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedSettingsViewModel @Inject constructor(
    private val savedSettingsUseCase: SavedSettingsUseCase
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun deleteSavedAllCountries() {
        viewModelScope.launch {
            savedSettingsUseCase.deleteAllSavedCountries()
        }
    }
}
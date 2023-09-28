package com.atakandalkiran.androidcountriesapp.ui.searchsettings

import com.atakandalkiran.androidcountriesapp.data.base.BaseViewModel
import com.atakandalkiran.androidcountriesapp.data.usecases.SearchSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchSettingsViewModel @Inject constructor(
    private val searchSettingsUseCase: SearchSettingsUseCase
) : BaseViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun clearSearchedHistory() {
        viewModelScope.launch {
            searchSettingsUseCase.clearSearchHistory()
        }
    }
}
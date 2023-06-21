package com.atakandalkiran.androidcountriesapp.ui.home

import androidx.lifecycle.*
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.repository.CountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.atakandalkiran.androidcountriesapp.data.Result
import com.atakandalkiran.androidcountriesapp.data.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private var viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var isChipSelected: Boolean = false

    val temporaryResponseList = ArrayList<CountryInfos?>()

    private val _countryProperties = MutableStateFlow<List<CountryInfos?>?>(null)
    val countryProperties = _countryProperties.asStateFlow()

    private val _navigateToDetail = MutableSharedFlow<CountryInfos?>()
    val navigateToDetail = _navigateToDetail.asSharedFlow()

    private val _connectionSuccess = MutableSharedFlow<Boolean?>()
    val connectionSuccess = _connectionSuccess.asSharedFlow()

    private val _loadingStatus = MutableSharedFlow<Boolean?>()
    val loadingStatus = _loadingStatus.asSharedFlow()

    init {
        getCountriesProperties()
    }

    fun filteringViaContinents(selectedContinent: String) {
        val filterList = if (selectedContinent == "All" || selectedContinent.isEmpty()) {
            temporaryResponseList
        } else {
            temporaryResponseList.filter { it?.continents?.contains(selectedContinent) == true }
        }
        isChipSelected = true
        _countryProperties.value = filterList
    }

    fun doneNavigating() {
        viewModelScope.launch {
            _navigateToDetail.emit(null)
        }
    }

    private fun doneConnecting() {
        viewModelScope.launch {
            _connectionSuccess.emit(true)
        }
    }

    private fun needRetryConnect() {
        viewModelScope.launch {
            _connectionSuccess.emit(false)
        }
    }

    private fun loadingBarDisplay(display: Boolean?) {
        viewModelScope.launch {
            _loadingStatus.emit(display)
        }
    }

    fun retryConnect() {
        doneConnecting()
        getCountriesProperties()
    }


    fun countryInfosButtonActions(countryInfos: CountryInfos, isFavorite: Boolean) {
        checkIsFavorite(countryInfos, isFavorite)

        when (isFavorite) {
            true -> {
                insertSavedCountries(countryInfos)
            }
            false -> {
                clearSavedCountries(countryInfos)
            }
        }
    }

    private fun checkIsFavorite(countryInfos: CountryInfos, isFavorite: Boolean) {
        temporaryResponseList.find {
            it == countryInfos
        }?.isFavorite = isFavorite
        _countryProperties.value = temporaryResponseList
    }

    private fun getCountriesProperties() {
        viewModelScope.launch {
            loadingBarDisplay(true)
            homeUseCase.getCountryProperties().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        temporaryResponseList.addAll(result.data)
                        _countryProperties.value = temporaryResponseList
                        doneConnecting()
                        loadingBarDisplay(false)
                    }
                    is Result.Failure -> {
                        needRetryConnect()
                        loadingBarDisplay(false)
                    }
                }
            }
        }
    }

    private fun insertSavedCountries(country: CountryInfos) {
        viewModelScope.launch {
            homeUseCase.insertOrReplaceItem(country)
        }
    }

    private fun clearSavedCountries(countryInfos: CountryInfos) {
        viewModelScope.launch {
            homeUseCase.deleteSavedCountries(countryInfos)
        }
    }
}

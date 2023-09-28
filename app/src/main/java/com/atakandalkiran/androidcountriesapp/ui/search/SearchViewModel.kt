package com.atakandalkiran.androidcountriesapp.ui.search

import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.atakandalkiran.androidcountriesapp.data.Result
import com.atakandalkiran.androidcountriesapp.data.base.BaseViewModel
import com.atakandalkiran.androidcountriesapp.data.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val temporaryResultList = ArrayList<CountryInfos?>()

    private val _searchHistory = searchUseCase
        .getSearchedCountries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
    val searchHistory: StateFlow<List<SearchHistoryModel>>
        get() = _searchHistory

    private val _searchStatus = MutableStateFlow<Boolean?>(false)
    val searchStatus = _searchStatus.asStateFlow()

    private val _countries = MutableStateFlow<List<CountryInfos?>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _searchProgressStatus = MutableStateFlow<Boolean?>(false)
    val searchProgressStatus = _searchProgressStatus.asStateFlow()

    private var lastSearchedCountry: String = ""

    fun saveSearchedCountry(keyword: String) {
        if (!_searchHistory.value.map { it.searchedKeyword }.contains(keyword)) {
            viewModelScope.launch {
                searchUseCase.saveSearchKeyword(SearchHistoryModel(0, keyword))
            }
        }
        lastSearchedCountry = keyword
        setSearchStatus(true)
        fetchData()
    }

    private fun fetchData() {
        serviceFetchingJob = viewModelScope.launch {
            searchProgressDisplay(true)
            searchUseCase.searchCountries(lastSearchedCountry).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        temporaryResultList.clear()
                        temporaryResultList.addAll(result.data)
                        _countries.value = temporaryResultList
                        setSearchStatus(null)
                        clearSearchBar()
                        searchProgressDisplay(false)
                    }
                    is Result.Failure -> {
                        setSearchStatus(true)
                        searchProgressDisplay(false)
                    }
                }
            }
        }
    }

    fun countryInfosButtonActions(countryInfos: CountryInfos, status: Boolean) {
        checkIsFavorite(countryInfos, status)
        when (status) {
            true -> {
                insertSavedCountries(countryInfos)
            }
            false -> {
                clearSavedCountry(countryInfos)
            }
        }
    }

    private fun checkIsFavorite(country: CountryInfos, status: Boolean) {
        temporaryResultList.find {
            it == country
        }?.isFavorite = status
        _countries.value = temporaryResultList
    }

    fun setSearchStatus(status: Boolean?) {
        _searchStatus.value = status
    }

    private fun searchProgressDisplay(status: Boolean?) {
        _searchProgressStatus.value = status
    }

    fun clearSearchBar() {
        lastSearchedCountry = ""
    }

    private fun insertSavedCountries(country: CountryInfos) {
        viewModelScope.launch {
            searchUseCase.insertOrReplaceItem(country)
        }
    }

    private fun clearSavedCountry(country: CountryInfos) {
        viewModelScope.launch {
            searchUseCase.deleteSavedCountries(country)
        }
    }

    fun clearSearchedCountries(searchedKeyword: SearchHistoryModel) {
        viewModelScope.launch {
            searchUseCase.clearSearchHistoryCountries(searchedKeyword)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

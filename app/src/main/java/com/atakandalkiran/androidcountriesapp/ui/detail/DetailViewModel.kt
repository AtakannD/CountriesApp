@file:Suppress("NAME_SHADOWING")

package com.atakandalkiran.androidcountriesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.room.util.newStringBuilder
import com.atakandalkiran.androidcountriesapp.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    private val _selectedCountry = MutableStateFlow<CountryInfos?>(null)
    val selectedCountry = _selectedCountry.asStateFlow()

    private val _uiData = MutableStateFlow<List<DetailUiModel>?>(null)
    val uiData = _uiData.asStateFlow()

    fun setCountry(selectedCountry: CountryInfos) {
        _selectedCountry.value = selectedCountry
        getUIModel(selectedCountry)
    }

    private fun getUIModel(countryInfos: CountryInfos) {

        val resultList = mutableListOf<DetailUiModel>()
        var index = 0
        val continent = newStringBuilder()
        val border = newStringBuilder()
        val capital = newStringBuilder()
        val timezone = newStringBuilder()
        val tld = newStringBuilder()

        resultList.add(DetailHeaderModel(id = index++, title = "Continent: "))
        countryInfos.continents.forEachIndexed { index, it ->
            continent.append(it)
            if (index != countryInfos.continents.lastIndex) {
                continent.append(", ")
            }
        }
        resultList.add(DetailItemModel(id = index++, item = continent.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = "Region: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countryRegion))

        resultList.add(DetailHeaderModel(id = index++, title = "Subregion: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countrySubregion))

        resultList.add(DetailHeaderModel(id = index++, title = "Borders: "))
        if (countryInfos.borders != null) {
            countryInfos.borders.forEachIndexed { index, it ->
                border.append(it)
                if (index != countryInfos.borders.lastIndex) {
                    border.append(", ")
                }
            }
        } else {
            border.append("There is no borders to another country.")
        }
        resultList.add(DetailItemModel(id = index++, item = border.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = "is Landlocked: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.isLandlocked))

        resultList.add(DetailHeaderModel(id = index++, title = "Capital: "))
        if (countryInfos.capital != null) {
            countryInfos.capital.forEachIndexed { index, it ->
                capital.append(it)
                if (index != countryInfos.capital.lastIndex) {
                    capital.append(", ")
                }
            }
        } else {
            capital.append("There is no capital city.")
        }
        resultList.add(DetailItemModel(id = index++, item = capital.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = "Demonyms: "))
        resultList.add(
            DetailItemModel(
                id = index++,
                item = countryInfos.countryDemonyms
            )
        )

        resultList.add(DetailHeaderModel(id = index++, title = "is Independent: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.isIndependent))

        resultList.add(DetailHeaderModel(id = index++, title = "is UN member: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.isUnMember))

        resultList.add(DetailHeaderModel(id = index++, title = "Area(km²): "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countryArea))

        resultList.add(DetailHeaderModel(id = index++, title = "Population: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countryPopulation))

        resultList.add(DetailHeaderModel(id = index++, title = "Timezones: "))
        countryInfos.timezones.forEachIndexed { index, it ->
            timezone.append(it)
            if (index != countryInfos.timezones.lastIndex) {
                timezone.append(", ")
            }
        }
        resultList.add(DetailItemModel(id = index++, item = timezone.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = "Driving Side: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.trafficSide))

        resultList.add(DetailHeaderModel(id = index++, title = "Calling Code: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.telefonDomain))

        resultList.add(DetailHeaderModel(id = index++, title = "ISO 3166 code: "))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.cca2.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = "Internet Domains: "))
        countryInfos.tld.forEachIndexed { index, it ->
            tld.append(it)
            if (index != countryInfos.tld.lastIndex) {
                tld.append(", ")
            }
        }
        resultList.add(DetailItemModel(id = index, item = tld.toString()))

        _uiData.value = resultList
    }
}
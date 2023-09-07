@file:Suppress("NAME_SHADOWING")

package com.atakandalkiran.androidcountriesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.room.util.newStringBuilder
import com.atakandalkiran.androidcountriesapp.data.model.*
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos.NullValueMessages.NO_BORDER_COUNTRY_MSG
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos.NullValueMessages.NO_CAPITAL_CITY_MSG
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_AREA
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_BORDERS
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_CALLING_CODE
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_CAPITAL_CITY
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_CONTINENT
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_DEMONYMS
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_DRIVING_SIDE
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_INTERNET_DOMAINS
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_ISO_3166_CODE
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_IS_INDEPENDENT
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_IS_LANDLOCKED
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_IS_UN_MEMBER
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_POPULATION
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_REGION
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_SUBREGION
import com.atakandalkiran.androidcountriesapp.ui.detail.Titles.TXT_TIMEZONES
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

        resultList.add(DetailHeaderModel(id = index++, title = TXT_CONTINENT))
        countryInfos.continents.forEachIndexed { index, it ->
            continent.append(it)
            if (index != countryInfos.continents.lastIndex) {
                continent.append(", ")
            }
        }
        resultList.add(DetailItemModel(id = index++, item = continent.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_REGION))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countryRegion))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_SUBREGION))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countrySubregion))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_BORDERS))
        if (countryInfos.borders != null) {
            countryInfos.borders.forEachIndexed { index, it ->
                border.append(it)
                if (index != countryInfos.borders.lastIndex) {
                    border.append(", ")
                }
            }
        } else {
            border.append(NO_BORDER_COUNTRY_MSG)
        }
        resultList.add(DetailItemModel(id = index++, item = border.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_IS_LANDLOCKED))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.isLandlocked))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_CAPITAL_CITY))
        if (countryInfos.capital != null) {
            countryInfos.capital.forEachIndexed { index, it ->
                capital.append(it)
                if (index != countryInfos.capital.lastIndex) {
                    capital.append(", ")
                }
            }
        } else {
            capital.append(NO_CAPITAL_CITY_MSG)
        }
        resultList.add(DetailItemModel(id = index++, item = capital.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_DEMONYMS))
        resultList.add(
            DetailItemModel(
                id = index++,
                item = countryInfos.countryDemonyms
            )
        )

        resultList.add(DetailHeaderModel(id = index++, title = TXT_IS_INDEPENDENT))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.isIndependent))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_IS_UN_MEMBER))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.isUnMember))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_AREA))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countryArea))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_POPULATION))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.countryPopulation))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_TIMEZONES))
        countryInfos.timezones.forEachIndexed { index, it ->
            timezone.append(it)
            if (index != countryInfos.timezones.lastIndex) {
                timezone.append(", ")
            }
        }
        resultList.add(DetailItemModel(id = index++, item = timezone.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_DRIVING_SIDE))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.trafficSide))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_CALLING_CODE))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.telefonDomain))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_ISO_3166_CODE))
        resultList.add(DetailItemModel(id = index++, item = countryInfos.cca2.toString()))

        resultList.add(DetailHeaderModel(id = index++, title = TXT_INTERNET_DOMAINS))
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
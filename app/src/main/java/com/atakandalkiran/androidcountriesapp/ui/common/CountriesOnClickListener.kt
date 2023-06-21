package com.atakandalkiran.androidcountriesapp.ui.common

import android.view.View
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos

interface CountriesOnClickListener {

    fun onItemClick(view: View, countryInfos: CountryInfos)

    fun onCurrentChanged()

    fun onSaveButtonClickListener(countryInfos: CountryInfos)

    fun onClearButtonClickListener(countryInfos: CountryInfos)

}
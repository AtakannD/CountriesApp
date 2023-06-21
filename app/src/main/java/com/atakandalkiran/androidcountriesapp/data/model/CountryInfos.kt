package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "country_storage_table",
    primaryKeys = ["country_common_names"]
)
@Parcelize
data class CountryInfos(
    @ColumnInfo("country_area") val area: Double,
    @ColumnInfo("country_borders") val borders: List<String?>?,
    @ColumnInfo("country_capitals") val capital: List<String?>?,
    @Embedded val car: Car?,
    @ColumnInfo("country_code") val cca2: String?,
    @ColumnInfo("country_continent") val continents: List<String?>,
    @Embedded val demonyms: Demonyms?,
    @Embedded val flags: Flags,
    @Embedded val idd: Idd?,
    @ColumnInfo("country_is_indipendent") val independent: Boolean,
    @ColumnInfo("country_is_landlocked") val landlocked: Boolean,
    @Embedded val maps: Maps,
    @Embedded val name: Name,
    @ColumnInfo("country_population") val population: Int,
    @ColumnInfo("country_region") val region: String?,
    @ColumnInfo("country_subregion") val subregion: String?,
    @ColumnInfo("country_timezones") val timezones: List<String>,
    @ColumnInfo("country_domain_extensions") val tld: List<String>,
    @ColumnInfo("country_is_unMember") val unMember: Boolean,
    @ColumnInfo("is_favorite") var isFavorite: Boolean = false
) : Parcelable {
    val flagPng: String
        get() = this.flags.png.orEmpty()
    val countryPopulation: String
        get() = this.population.toString()
    val countryArea: String
        get() = this.area.toString()
    val isIndependent: String
        get() = this.independent.toString().replaceFirstChar(Char::uppercase)
    val isUnMember: String
        get() = this.unMember.toString().replaceFirstChar(Char::uppercase)
    val isLandlocked: String
        get() = this.landlocked.toString().replaceFirstChar(Char::uppercase)
    val trafficSide: String
        get() = this.car?.side!!.replaceFirstChar(Char::uppercase)
}

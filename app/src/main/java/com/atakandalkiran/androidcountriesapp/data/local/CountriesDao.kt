package com.atakandalkiran.androidcountriesapp.data.local

import androidx.room.*
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("SELECT EXISTS(SELECT * FROM country_storage_table WHERE country_common_names LIKE '%' || :searchedKeyword || '%')")
    suspend fun isSearchCountryExist(searchedKeyword: String): Boolean

    @Query("SELECT * FROM country_storage_table")
    fun getLocalCountryInformation(): Flow<List<CountryInfos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryInfos: List<CountryInfos>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryInfos: CountryInfos)

    @Delete
    suspend fun deleteSavedCountry(countryInfos: CountryInfos)

    @Query("DELETE FROM country_storage_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyModel: SearchHistoryModel)

    @Query("SELECT * FROM search_history_storage ORDER BY id DESC")
    fun getSearchHistoryCountries(): Flow<List<SearchHistoryModel>>

    @Query("DELETE FROM search_history_storage")
    suspend fun clearSearchHistory()

    @Delete
    suspend fun clearSearchHistoryCountry(searchedKeyword: SearchHistoryModel)
}
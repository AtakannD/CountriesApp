package com.atakandalkiran.androidcountriesapp.data.local

import androidx.room.*
import com.atakandalkiran.androidcountriesapp.data.Result
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("select exists(select * from country_storage_table where country_common_names LIKE '%' || :searchedKeyword || '%')")
    suspend fun isSearchCountryExist(searchedKeyword: String): Boolean

    @Query("select * from country_storage_table")
    fun getLocalCountryInformation(): Flow<List<CountryInfos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryInfos: List<CountryInfos>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryInfos: CountryInfos)

    @Delete
    suspend fun deleteSavedCountry(countryInfos: CountryInfos)

    @Query("delete from country_storage_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyModel: SearchHistoryModel)

    @Query("select * from search_history_storage order by id desc")
    fun getSearchHistoryCountries(): Flow<List<SearchHistoryModel>>

    @Query("delete from search_history_storage")
    suspend fun clearSearchHistory()

    @Delete
    suspend fun clearSearchHistoryCountry(searchedKeyword: SearchHistoryModel)
}
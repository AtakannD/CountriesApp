package com.atakandalkiran.androidcountriesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_history_storage"
)
data class SearchHistoryModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("searched_country_name") val searchedKeyword: String
)

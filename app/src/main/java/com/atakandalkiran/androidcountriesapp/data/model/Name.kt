package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    @PrimaryKey
    @ColumnInfo("country_common_names") val common: String,
    @ColumnInfo("country_official_names") val official: String
) : Parcelable
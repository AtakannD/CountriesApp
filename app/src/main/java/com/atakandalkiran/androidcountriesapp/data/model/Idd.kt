package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Idd(
    @ColumnInfo("country_phone_domains") val root: String?,
    @ColumnInfo("country_internet_domains") val suffixes: List<String?>?
) : Parcelable
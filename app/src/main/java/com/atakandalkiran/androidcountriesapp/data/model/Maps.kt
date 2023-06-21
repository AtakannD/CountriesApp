package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Maps(
    @ColumnInfo("country_maps") val googleMaps: String?,
) : Parcelable
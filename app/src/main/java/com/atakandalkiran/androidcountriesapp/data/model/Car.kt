package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    @ColumnInfo("country_traffic_side") val side: String?,
    @ColumnInfo("country_traffic_code") val signs: List<String?>
) : Parcelable
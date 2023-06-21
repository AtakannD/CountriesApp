package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemonymsSubsets(
    @ColumnInfo("country_demonyms_female") val f: String?,
    @ColumnInfo("country_demonyms_male") val m: String?
) : Parcelable
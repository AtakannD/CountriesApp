package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flags(
    @ColumnInfo("country_flag_description") val alt: String?,
    @ColumnInfo("country_flag_png") val png: String?,
    @ColumnInfo("country_flag_svg") val svg: String?
) : Parcelable
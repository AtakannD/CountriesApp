package com.atakandalkiran.androidcountriesapp.data.model

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class Demonyms(
    @Embedded val eng: DemonymsSubsets?
) : Parcelable
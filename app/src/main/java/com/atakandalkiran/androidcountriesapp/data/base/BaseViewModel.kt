package com.atakandalkiran.androidcountriesapp.data.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    lateinit var serviceFetchingJob : Job

    fun isFetchingActive() = serviceFetchingJob.isActive
}
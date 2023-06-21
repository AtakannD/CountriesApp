package com.atakandalkiran.androidcountriesapp.ui.search

import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel

interface SearchHistoryItemClickListener {

    fun onClick(historyItem: SearchHistoryModel)

    fun onClearButtonClick(searchedKeyword: SearchHistoryModel)
}
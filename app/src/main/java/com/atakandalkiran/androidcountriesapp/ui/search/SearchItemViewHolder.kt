package com.atakandalkiran.androidcountriesapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import com.atakandalkiran.androidcountriesapp.databinding.SearchHistoryItemLayoutBinding

class SearchHistoryViewHolder(
    private val binding: SearchHistoryItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        clickListener: SearchHistoryItemClickListener,
        country: SearchHistoryModel,
        onClear: (Int) -> Unit
    ) {
        binding.country = country
        binding.clickListener = clickListener
        binding.searchHistoryClearButton.setOnClickListener {
            clickListener.onClearButtonClick(country)
            onClear(adapterPosition)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SearchHistoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchHistoryItemLayoutBinding.inflate(layoutInflater, parent, false)
            return SearchHistoryViewHolder(binding)
        }
    }
}
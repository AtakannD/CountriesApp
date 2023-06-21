package com.atakandalkiran.androidcountriesapp.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel

class SearchAdapter(private val clickListener: SearchHistoryItemClickListener) :
    ListAdapter<SearchHistoryModel, SearchHistoryViewHolder>
        (SearchHistoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position)) {
            notifyItemChanged(it)
        }
    }

    class SearchHistoryDiffCallback : DiffUtil.ItemCallback<SearchHistoryModel>() {

        override fun areItemsTheSame(
            oldItem: SearchHistoryModel,
            newItem: SearchHistoryModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: SearchHistoryModel,
            newItem: SearchHistoryModel
        ) = oldItem == newItem
    }
}

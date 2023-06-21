package com.atakandalkiran.androidcountriesapp.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos

class CountriesRecyclerViewAdapter(private val clickListener: CountriesOnClickListener) :
    ListAdapter<CountryInfos, CountriesItemViewHolder>(CountriesItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesItemViewHolder {
        return CountriesItemViewHolder.from(parent)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<CountryInfos>,
        currentList: MutableList<CountryInfos>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        clickListener.onCurrentChanged()
    }

    override fun onBindViewHolder(holder: CountriesItemViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position)) {
            notifyItemChanged(it)
        }
    }
}

class CountriesItemDiffCallback : DiffUtil.ItemCallback<CountryInfos>() {
    override fun areItemsTheSame(
        oldItem: CountryInfos,
        newItem: CountryInfos
    ): Boolean {
        return oldItem.name.common == newItem.name.common
    }

    override fun areContentsTheSame(
        oldItem: CountryInfos,
        newItem: CountryInfos
    ): Boolean {
        return oldItem == newItem
    }
}


package com.atakandalkiran.androidcountriesapp.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.databinding.CountryInfosItemLayoutBinding

class CountriesItemViewHolder(
    private val binding: CountryInfosItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        clickListener: CountriesOnClickListener,
        countryInfos: CountryInfos,
        onSavedClick: (Int) -> Unit
    ) {

        binding.country = countryInfos
        binding.clickListener = clickListener
        binding.savedButton.setOnClickListener {

            clickListener.onSaveButtonClickListener(countryInfos)

            countryInfos.isFavorite = true

            onSavedClick(adapterPosition)

        }

        binding.clearButton.setOnClickListener {

            clickListener.onClearButtonClickListener(countryInfos)

            onSavedClick(adapterPosition)

            countryInfos.isFavorite = false
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CountriesItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CountryInfosItemLayoutBinding.inflate(layoutInflater, parent, false)
            return CountriesItemViewHolder(binding)
        }
    }
}
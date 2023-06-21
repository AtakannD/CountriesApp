package com.atakandalkiran.androidcountriesapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atakandalkiran.androidcountriesapp.data.model.DetailHeaderModel
import com.atakandalkiran.androidcountriesapp.data.model.DetailItemModel
import com.atakandalkiran.androidcountriesapp.databinding.LayoutDetailUiModelHeaderBinding
import com.atakandalkiran.androidcountriesapp.databinding.LayoutDetailUiModelItemBinding

class DetailUiTitleViewHolder(
    private val binding: LayoutDetailUiModelHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        model: DetailHeaderModel
    ) {
        binding.textHeader.text = model.title
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): DetailUiTitleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LayoutDetailUiModelHeaderBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return DetailUiTitleViewHolder(binding)
        }
    }
}

class DetailUiItemViewHolder(
    private val binding: LayoutDetailUiModelItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: DetailItemModel) {
        binding.model = model
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): DetailUiItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LayoutDetailUiModelItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return DetailUiItemViewHolder(binding)
        }
    }
}


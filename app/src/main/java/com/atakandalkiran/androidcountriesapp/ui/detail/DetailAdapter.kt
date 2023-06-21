package com.atakandalkiran.androidcountriesapp.ui.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atakandalkiran.androidcountriesapp.data.model.DetailHeaderModel
import com.atakandalkiran.androidcountriesapp.data.model.DetailItemModel
import com.atakandalkiran.androidcountriesapp.data.model.DetailUiModel

class DetailAdapter : ListAdapter<DetailUiModel, RecyclerView.ViewHolder>(DetailDiffCallBack()) {

    private val headerViewType = 0
    private val itemViewType = 1
    private val illegalStateExplanation =
        "DetailAdapter: Cannot create viewHolder for unknown view type"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            headerViewType -> DetailUiTitleViewHolder.from(parent)
            itemViewType -> DetailUiItemViewHolder.from(parent)
            else -> throw IllegalStateException(illegalStateExplanation)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            headerViewType -> (holder as? DetailUiTitleViewHolder)?.bind(
                getItem(position) as DetailHeaderModel
            )
            itemViewType -> (holder as? DetailUiItemViewHolder)?.bind(
                getItem(position) as DetailItemModel
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is DetailHeaderModel -> headerViewType
        else -> itemViewType
    }

    class DetailDiffCallBack : DiffUtil.ItemCallback<DetailUiModel>() {

        override fun areItemsTheSame(
            oldItem: DetailUiModel,
            newItem: DetailUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DetailUiModel,
            newItem: DetailUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


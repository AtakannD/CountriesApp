package com.atakandalkiran.androidcountriesapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesRecyclerViewAdapter
import com.bumptech.glide.Glide

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CountryInfos?>?) {
    val adapter = recyclerView.adapter as CountriesRecyclerViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, countryInfos: CountryInfos?) {
    val url = countryInfos?.flagPng
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}
package com.atakandalkiran.androidcountriesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.atakandalkiran.androidcountriesapp.R
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.databinding.FragmentHomeBinding
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesOnClickListener
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesRecyclerViewAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), CountriesOnClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: CountriesRecyclerViewAdapter

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel

        adapter = CountriesRecyclerViewAdapter(this)

        binding.recyclerView.adapter = adapter

        setupObservers()

        setupChips()

        return binding.root
    }

    override fun onCurrentChanged() {
        if (viewModel.isChipSelected) {
            binding.recyclerView.scrollToPosition(0)
            viewModel.isChipSelected = false
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToDetail.collectLatest {
                    this@HomeFragment
                        .findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it!!))
                    viewModel.doneNavigating()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionSuccess.collect {
                    binding.connectionErrorGroup.isVisible = it != true
                    binding.connectionSuccessGroup.isVisible = it != false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingStatus.collect {
                    binding.loadingStatus.isVisible = it != false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.countryProperties.collectLatest {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun setupChips() {
        binding.containerChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            group.findViewById<Chip>(selectedChipId)?.let {
                viewModel.filteringViaContinents(it.text.toString())
            }
        }
    }

    override fun onItemClick(view: View, countryInfos: CountryInfos) {
        this.findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(countryInfos))
    }

    override fun onSaveButtonClickListener(countryInfos: CountryInfos) {
        viewModel.countryInfosButtonActions(countryInfos, true)
    }

    override fun onClearButtonClickListener(countryInfos: CountryInfos) {
        viewModel.countryInfosButtonActions(countryInfos, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

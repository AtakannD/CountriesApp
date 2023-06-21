package com.atakandalkiran.androidcountriesapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.atakandalkiran.androidcountriesapp.MainActivity
import com.atakandalkiran.androidcountriesapp.R
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel
import com.atakandalkiran.androidcountriesapp.databinding.FragmentSearchBinding
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesOnClickListener
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesRecyclerViewAdapter
import com.atakandalkiran.androidcountriesapp.ui.searchsettings.SearchSettingsFragment
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchHistoryItemClickListener, CountriesOnClickListener {

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchHistoryAdapter: SearchAdapter
    private lateinit var searchResultAdapter: CountriesRecyclerViewAdapter

    private var _binding: FragmentSearchBinding? = null
    val binding: FragmentSearchBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_search_fragment)
            menu.findItem(R.id.settings).setOnMenuItemClickListener {
                openBottomSheet(); true
            }
        }

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        setAdapters()

        setSearchListener()

        setObservers()

        return binding.root
    }

    private fun setAdapters() {
        searchResultAdapter = CountriesRecyclerViewAdapter(this)
        searchHistoryAdapter = SearchAdapter(this)

        lifecycleScope.launch {
            viewModel.searchStatus.collect { searchStatus ->
                if (searchStatus == false) {
                    binding.searchHistoryRecyclerView.adapter = searchHistoryAdapter
                } else {
                    binding.searchHistoryRecyclerView.adapter = searchResultAdapter
                }
            }
        }
    }

    private fun setSearchListener() {
        binding.searchText.setOnEditorActionListener { view, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    (requireActivity() as MainActivity).hideKeyboard(view)
                    viewModel.clearSearchBar()
                    viewModel.saveSearchedCountry(view.text.toString())
                    binding.searchHistoryRecyclerView.adapter = searchResultAdapter
                    false
                }
                else -> true
            }
        }
        binding.searchField.setEndIconOnClickListener {
            viewModel.setSearchStatus(false)
            binding.searchText.setText("")
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.searchHistory.collectLatest {
                searchHistoryAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.countries.collectLatest {
                searchResultAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchStatus.collectLatest {
                    when (it) {
                        false -> {
                            binding.searchHistoryRecyclerView.adapter = searchHistoryAdapter
                        }
                        else -> {
                            binding.searchHistoryRecyclerView.adapter = searchResultAdapter
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchProgressStatus.collectLatest {
                    binding.searchLoadingBar.isVisible = it != false
                }
            }
        }
    }

    private fun openBottomSheet() {
        SearchSettingsFragment.newInstance().show(
            requireActivity().supportFragmentManager,
            SearchSettingsFragment.TAG
        )
    }


    override fun onClick(historyItem: SearchHistoryModel) {
        binding.searchText.apply {
            setText(historyItem.searchedKeyword)
            onEditorAction(EditorInfo.IME_ACTION_SEARCH)
        }
    }

    override fun onClearButtonClick(searchedKeyword: SearchHistoryModel) {
        viewModel.clearSearchedCountries(searchedKeyword)
    }

    override fun onItemClick(view: View, countryInfos: CountryInfos) {
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)

        val directions =
            SearchFragmentDirections.actionSearchFragmentToDetailFragment2(countryInfos)
        this.findNavController().navigate(directions)
    }

    override fun onSaveButtonClickListener(countryInfos: CountryInfos) {
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)

        viewModel.countryInfosButtonActions(countryInfos, true)
    }

    override fun onCurrentChanged() = Unit

    override fun onClearButtonClickListener(countryInfos: CountryInfos) {
        viewModel.countryInfosButtonActions(countryInfos, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
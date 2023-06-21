package com.atakandalkiran.androidcountriesapp.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.atakandalkiran.androidcountriesapp.R
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.databinding.FragmentSavedBinding
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesOnClickListener
import com.atakandalkiran.androidcountriesapp.ui.common.CountriesRecyclerViewAdapter
import com.atakandalkiran.androidcountriesapp.ui.savedsettings.SaveSettingsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : Fragment(), CountriesOnClickListener {

    private lateinit var viewModel: SavedViewModel
    private lateinit var adapter: CountriesRecyclerViewAdapter

    private var _binding: FragmentSavedBinding? = null
    val binding: FragmentSavedBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_saved, container, false
        )

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_saved_fragment)
            menu.findItem(R.id.settings).setOnMenuItemClickListener {
                openBottomSheet(); true
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this)[SavedViewModel::class.java]
        binding.viewModel = viewModel

        adapter = CountriesRecyclerViewAdapter(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.countries.collectLatest {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

    private fun openBottomSheet() {
        SaveSettingsFragment.newInstance().show(
            requireActivity().supportFragmentManager,
            SaveSettingsFragment.TAG
        )
    }

    override fun onItemClick(view: View, countryInfos: CountryInfos) {
        val directions = SavedFragmentDirections.actionSavedFragmentToDetailFragment3(countryInfos)
        this.findNavController().navigate(directions)
    }

    override fun onSaveButtonClickListener(countryInfos: CountryInfos) = Unit

    override fun onCurrentChanged() = Unit

    override fun onClearButtonClickListener(countryInfos: CountryInfos) {
        viewModel.clearSavedCountries(countryInfos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
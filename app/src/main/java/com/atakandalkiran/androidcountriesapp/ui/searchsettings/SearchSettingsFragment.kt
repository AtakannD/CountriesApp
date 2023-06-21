package com.atakandalkiran.androidcountriesapp.ui.searchsettings

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.atakandalkiran.androidcountriesapp.R
import com.atakandalkiran.androidcountriesapp.databinding.LayoutSearchSettingsMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSettingsFragment : BottomSheetDialogFragment() {

    val viewModel: SearchSettingsViewModel by viewModels()

    private var _binding: LayoutSearchSettingsMenuBinding? = null
    val binding: LayoutSearchSettingsMenuBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.layout_search_settings_menu, container, false
        )

        setupButton()

        return binding.root
    }

    private fun setupButton() {
        binding.clearSearchHistoryButton.setOnClickListener {
            viewModel.clearSearchedHistory()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        fun newInstance() = SearchSettingsFragment()
    }
}

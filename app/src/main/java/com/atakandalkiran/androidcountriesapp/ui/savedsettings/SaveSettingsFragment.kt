package com.atakandalkiran.androidcountriesapp.ui.savedsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.atakandalkiran.androidcountriesapp.R
import com.atakandalkiran.androidcountriesapp.databinding.LayoutSavedSettingsMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveSettingsFragment : BottomSheetDialogFragment() {

    val viewModel: SavedSettingsViewModel by viewModels()

    private var _binding: LayoutSavedSettingsMenuBinding? = null
    val binding: LayoutSavedSettingsMenuBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.layout_saved_settings_menu, container, false
        )

        setupButton()

        return binding.root
    }

    private fun setupButton() {
        binding.clearSearchHistoryButton.setOnClickListener {
            viewModel.deleteSavedAllCountries()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        fun newInstance() = SaveSettingsFragment()
    }
}
package com.example.thelandsurveyor.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thelandsurveyor.R
import com.example.thelandsurveyor.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        settingsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val gnss = listOf("TRIMBLE 5700II 4906165579", "TRIMBLE 5700II 4906165579", "TRIMBLE 5700II 4906165579")
        val antenna = listOf("TRIMBLE ZEPHIR 50266723", "TRIMBLE ZEPHIR 50266723", "TRIMBLE ZEPHIR 50266723")
        val gnssAdapter = ArrayAdapter(requireContext(), R.layout.list_item, gnss)
        val antennaAdapter = ArrayAdapter(requireContext(), R.layout.list_item, antenna)
        binding.autoCompleteTextView.setAdapter(gnssAdapter)
        binding.antenaAutoCompleteTextView.setAdapter(antennaAdapter)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.assesment.groww.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.assesment.groww.R
import com.assesment.groww.databinding.FragmentCharacterListBinding
import com.assesment.groww.databinding.FragmentSplashScreenBinding
import com.assesment.groww.viewmodels.FragmentState
import com.assesment.groww.viewmodels.HomeViewModel
import timber.log.Timber

class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels({requireActivity()})


    private fun setupObservers() {
        viewModel.isFetched.observe(viewLifecycleOwner){ isFetched ->
            viewModel.setFragment(FragmentState.CHARACTERS_LIST_STATE)
            if (isFetched.first) {
                Timber.d("Data fetched successfully")
            } else {
                Timber.e("Unable to fetch data = ${isFetched.second}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.getCharacters()

        return view
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    override fun onPause() {
        super.onPause()
        viewModel.isFetched.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
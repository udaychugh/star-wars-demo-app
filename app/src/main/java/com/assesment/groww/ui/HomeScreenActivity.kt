package com.assesment.groww.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.assesment.groww.R
import com.assesment.groww.databinding.ActivityHomeScreenBinding
import com.assesment.groww.databinding.FilterBottomSheetBinding
import com.assesment.groww.ui.fragments.CharacterInfoFragment
import com.assesment.groww.ui.fragments.CharacterListFragment
import com.assesment.groww.ui.fragments.SplashScreenFragment
import com.assesment.groww.viewmodels.FragmentState
import com.assesment.groww.viewmodels.FragmentState.*
import com.assesment.groww.viewmodels.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private val viewModel: HomeViewModel by viewModels()

    private fun setupObservers() {
        viewModel.fragmentState.observe(this) { state->
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            when(state) {
                SPLASH_STATE -> {
                    binding.homeScreenToolbarRl.visibility = View.GONE
                    val fragment = SplashScreenFragment()
                    transaction.replace(R.id.screenFl, fragment)
                    transaction.commit()
                }
                CHARACTERS_LIST_STATE -> {
                    binding.homeScreenToolbarRl.visibility = View.VISIBLE
                    binding.filterIcon.visibility = View.VISIBLE
                    val fragment = CharacterListFragment()
                    transaction.replace(R.id.screenFl, fragment)
                    transaction.commit()
                }
                CHARACTER_INFO_STATE -> {
                    binding.homeScreenToolbarRl.visibility = View.VISIBLE
                    binding.filterIcon.visibility = View.GONE
                    val fragment = CharacterInfoFragment()
                    transaction.replace(R.id.screenFl, fragment)
                    transaction.commit()
                }
                else -> {

                }
            }
        }

        viewModel.toolbarTitle.observe(this) { title->
            binding.toolbarHeaderTv.text = title
        }
    }

    private fun openFilterBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val dialogBinding = FilterBottomSheetBinding.inflate(layoutInflater)
        val view = dialogBinding.root

        dialogBinding.nameFilter.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.genderFilter.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.ageFilter.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.createFilter.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.updatedFilter.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        binding.filterIcon.setOnClickListener{
            openFilterBottomSheet()
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentState = viewModel.getCurrentFragment()
                if (currentState == CHARACTER_INFO_STATE) {
                    viewModel.setToolbarTitle(getString(R.string.toolbar_title))
                    viewModel.setFragment(CHARACTERS_LIST_STATE)
                } else {
                    finish()
                }
            }

        })

    }
}
package com.assesment.groww.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.assesment.groww.adapters.FilmAdapter
import com.assesment.groww.databinding.FragmentCharacterInfoBinding
import com.assesment.groww.viewmodels.HomeViewModel
import timber.log.Timber

class CharacterInfoFragment : Fragment() {

    private var _binding: FragmentCharacterInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels({requireActivity()})

    private lateinit var filmAdapter: FilmAdapter

    private fun setupObservers() {
        viewModel.filmResponse.observe(viewLifecycleOwner){ film ->
            if (film.second != null) {
                Timber.e("Error in loading Films")
                return@observe
            }

            filmAdapter.appendItemIntoList(film.first!!)
        }
    }

    private fun callForFilms(films: List<String>) {
        for (film in films) {
            val regex = Regex("""/films/(\d+)/""")
            val filmNumber = regex.find(film)
            viewModel.getCharacterFilms(filmNumber?.groups?.get(1)?.value ?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        val charData = viewModel.getCharacterFullData()

        binding.charHeight.text = charData.height
        binding.charMass.text = charData.mass
        binding.charHair.text = charData.hairColor
        binding.charSkin.text = charData.skinColor
        binding.charEye.text = charData.eyeColor
        binding.charBirth.text = charData.birthYear
        binding.charGender.text = charData.gender

        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.filmsRv.layoutManager = mLayoutManager
        filmAdapter = FilmAdapter(ArrayList())
        binding.filmsRv.adapter = filmAdapter

        callForFilms(charData.films)

        return view
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    override fun onPause() {
        super.onPause()
        viewModel.filmResponse.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
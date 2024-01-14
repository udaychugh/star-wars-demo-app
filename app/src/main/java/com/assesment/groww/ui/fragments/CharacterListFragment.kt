package com.assesment.groww.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.assesment.groww.adapters.CharAdapter
import com.assesment.groww.databinding.FragmentCharacterListBinding
import com.assesment.groww.viewmodels.FragmentState
import com.assesment.groww.viewmodels.HomeViewModel

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels({requireActivity()})
    private val characterItemClickHandler = CharacterItemClickHandler()
    private lateinit var dashboardItemAdapter: CharAdapter

    private fun appendCharactersIntoList() {
        val characterList = ArrayList<CharAdapter.CharacterItems>()
        characterList.clear()
        characterList.addAll(viewModel.getCharactersFromLocalDb())
        dashboardItemAdapter.appendItems(characterList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        val view = binding.root

        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.starWarsCharRv.layoutManager = mLayoutManager
        dashboardItemAdapter = CharAdapter(ArrayList(), characterItemClickHandler)
        binding.starWarsCharRv.adapter = dashboardItemAdapter

        appendCharactersIntoList()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class CharacterItemClickHandler: CharAdapter.CharacterItemListener {
        override fun onItemClickListener(charData: CharAdapter.CharacterItems) {
            viewModel.setToolbarTitle(charData.name)
            viewModel.setFragment(FragmentState.CHARACTER_INFO_STATE)
            viewModel.setCharacterFullData(charData)
            dashboardItemAdapter.clearItems()
        }


    }

}
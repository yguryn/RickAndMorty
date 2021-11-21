package com.example.rickandmorty.ui.Location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentLocationsDetailsBinding
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Location
import com.example.rickandmorty.ui.Character.CharacterLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragmentDetails() : Fragment() {

    private var _binding: FragmentLocationsDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels()
    private val charactersId = mutableListOf<Int>()
    private var characterList = mutableListOf<Character>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterLoadStateAdapter
    var char: Location? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var charId: Int = 1
        arguments?.let {
            charId = LocationFragmentDetailsArgs.fromBundle(it).id
        }
        char = viewModel.getLocationById(charId)

        char?.residents?.forEach {
            charactersId.add(it.substringAfterLast("/").toInt())
        }

        characterList = viewModel.getCharactersById(charactersId)
        recyclerView = view.findViewById(R.id.locationDetailRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CharacterLoadStateAdapter()
        recyclerView.adapter = adapter
        CharacterLoadStateAdapter.state = 3
        lifecycleScope.launch {
            adapter.submitList(characterList)
        }

        binding.apply {
            locationDetailName.text = char?.name
            locationDetailCreated.text = char?.created
            locationDetailDimension.text = char?.dimension
            locationDetailType.text = char?.type
        }
    }
}
package com.example.rickandmorty.ui.Episode

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
import com.example.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Episode
import com.example.rickandmorty.ui.Character.CharacterLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragmentDetails() : Fragment() {

    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodesViewModel by activityViewModels()
    private val charactersId = mutableListOf<Int>()
    private var characterList = mutableListOf<Character>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterLoadStateAdapter
    var char: Episode? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var charId: Int = 1
        arguments?.let {
            charId = EpisodeFragmentDetailsArgs.fromBundle(it).id
        }
        char = viewModel.getEpisodeById(charId)

        char?.characters?.forEach {
            charactersId.add(it.substringAfterLast("/").toInt())
        }

        characterList = viewModel.getCharactersById(charactersId)

        recyclerView = view.findViewById(R.id.episodeDetailRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CharacterLoadStateAdapter()
        recyclerView.adapter = adapter
        CharacterLoadStateAdapter.state = 2
        lifecycleScope.launch {
            adapter.submitList(characterList)
        }

        binding.apply {
            episodeDetailName.text = char?.name
            episodeDetailCreated.text = char?.created
            episodeDetailNumber.text = char?.episode
            episodeDetailAirDate.text = char?.air_date
        }
    }
}
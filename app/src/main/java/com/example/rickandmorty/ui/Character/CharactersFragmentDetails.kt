package com.example.rickandmorty.ui.Character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Episode
import com.example.rickandmorty.ui.Episode.EpisodeLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragmentDetails : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EpisodeLoadStateAdapter
    private val episodeId = mutableListOf<Int>()
    private var episodeList = mutableListOf<Episode>()
    var char: Character? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var charId: Int = 1
        arguments?.let {
            charId = CharactersFragmentDetailsArgs.fromBundle(it).id
        }
        char = viewModel.getCharacterById(charId)

        char?.episode?.forEach {
            episodeId.add(it.substringAfterLast("/").toInt())
        }

        episodeList = viewModel.getEpisodeById(episodeId)

        recyclerView = view.findViewById(R.id.characterDetailsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter = EpisodeLoadStateAdapter()
        recyclerView.adapter = adapter
        EpisodeLoadStateAdapter.state = 2
        lifecycleScope.launch {
            adapter.submitList(episodeList)
        }

        binding.apply {
            Log.d("BBB", "${char?.name}")
            characterDetailName.text = char?.name
            characterDetailGender.text = char?.gender
            characterDetailSpecies.text = char?.species
            characterDetailStatus.text = char?.status
            characterDetailName.text = char?.name
            characterDetailCreated.text = char?.created
            characterDetailImage.load(char?.image) {
                crossfade(true)
            }
        }
    }
}
package com.example.rickandmorty.ui.Character

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by activityViewModels()
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var characterLoadAdapter: CharacterLoadStateAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = CharactersFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.d("BBB", "sdfdg")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterAdapter = CharacterAdapter()
        characterLoadAdapter = CharacterLoadStateAdapter()
        recyclerView = view.findViewById(R.id.charactersRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        setupAdapter()
    }

    private fun setupAdapter() {
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (!isConnected) {
            recyclerView.adapter = characterLoadAdapter
            lifecycleScope.launch {
                viewModel.characters.observe(requireActivity()) {
                    characterLoadAdapter.submitList(it.data)
                    Log.d("AAA", "${it.data}")
                }
            }
        } else {
            recyclerView.adapter = characterAdapter
            CharacterLoadStateAdapter.state = 1
            lifecycleScope.launch {
                viewModel.listData.collect { pagingData ->
                    characterAdapter.submitData(pagingData)
                    Log.d("AAA", "$pagingData")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


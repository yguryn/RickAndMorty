package com.example.rickandmorty.ui.Episode

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodesFragment : Fragment(R.layout.fragment_episodes) {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodesViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var episodeLoadStateAdapter: EpisodeLoadStateAdapter

    companion object {
        fun newInstance() = EpisodesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.d("BBB", "sdfdg")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeAdapter = EpisodeAdapter()
        episodeLoadStateAdapter = EpisodeLoadStateAdapter()
        Log.d("BBB", "EpisodeCreAted")
        recyclerView = view.findViewById(R.id.episodeRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        setupAdapter()
    }

    private fun setupAdapter() {
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(!isConnected) {
            EpisodeLoadStateAdapter.state = 1
            recyclerView.adapter = episodeLoadStateAdapter
            lifecycleScope.launch {
                viewModel.episodes.observe(requireActivity()) {
                    episodeLoadStateAdapter.submitList(it.data)
                }
            }
        } else {
            recyclerView.adapter = episodeAdapter
            lifecycleScope.launch {
                viewModel.listData.collect { pagingData ->
                    episodeAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
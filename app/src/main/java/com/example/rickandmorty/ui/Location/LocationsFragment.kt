package com.example.rickandmorty.ui.Location

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationsFragment : Fragment(R.layout.fragment_locations) {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels()
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var locationLoadAdapter: LocationLoadStateAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = LocationsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationAdapter = LocationAdapter()
        locationLoadAdapter = LocationLoadStateAdapter()
        recyclerView = view.findViewById(R.id.locationRecyclerView)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        setupAdapter()
    }

    private fun setupAdapter() {
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (!isConnected) {
            recyclerView.adapter = locationLoadAdapter
            lifecycleScope.launch {
                viewModel.locations.observe(requireActivity()) {
                    locationLoadAdapter.submitList(it.data)
                }
            }
        } else {
            recyclerView.adapter = locationAdapter
            lifecycleScope.launch {
                viewModel.listData.collect { pagingData ->
                    locationAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
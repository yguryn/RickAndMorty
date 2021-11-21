package com.example.rickandmorty.ui.Location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemLocationBinding
import com.example.rickandmorty.models.Location

class LocationLoadStateAdapter :
    ListAdapter<Location, LocationLoadStateAdapter.MyViewHolder>(diffCallback) {

    inner class MyViewHolder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            locationName.text = "${currentItem?.name}"
            locationDimension.text = "${currentItem?.dimension}"
            locationType.text = "${currentItem?.type}"
            holder.itemView.setOnClickListener {
                val action =
                    LocationsFragmentDirections.actionLocationsFragmentToLocationFragmentDetails(
                        currentItem!!.id
                    )
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
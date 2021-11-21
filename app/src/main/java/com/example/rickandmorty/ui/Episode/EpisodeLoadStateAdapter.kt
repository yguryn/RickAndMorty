package com.example.rickandmorty.ui.Episode

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemEpisodeBinding
import com.example.rickandmorty.models.Episode
import com.example.rickandmorty.ui.Character.CharactersFragmentDetailsDirections

class EpisodeLoadStateAdapter :
    ListAdapter<Episode, EpisodeLoadStateAdapter.MyViewHolder>(diffCallback) {

    inner class MyViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        var state = 1
        val diffCallback = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            episodeName.text = "${currentItem?.name}"
            episodeAirDate.text = "${currentItem?.air_date}"
            episodeNumber.text = "${currentItem?.episode}"
            holder.itemView.setOnClickListener {
                val action: NavDirections
                when (state) {
                    1 -> {
                        action =
                            EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeFragmentDetails(
                                currentItem!!.id
                            )
                    }
                    else -> {
                        action =
                            CharactersFragmentDetailsDirections.actionCharactersFragmentDetailsToEpisodeFragmentDetails(
                                currentItem.id
                            )

                    }
                }
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
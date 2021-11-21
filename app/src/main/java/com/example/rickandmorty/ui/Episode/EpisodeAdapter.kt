package com.example.rickandmorty.ui.Episode

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemEpisodeBinding
import com.example.rickandmorty.models.Episode

class EpisodeAdapter : PagingDataAdapter<Episode, EpisodeAdapter.MyViewHolder>(diffCallback) {

    inner class MyViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
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
        Log.d("BBB", "adapter")
        holder.binding.apply {
            episodeName.text = "${currentItem?.name}"
            episodeAirDate.text = "${currentItem?.air_date}"
            episodeNumber.text = "${currentItem?.episode}"
            holder.itemView.setOnClickListener {
                val action =
                    EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeFragmentDetails(
                        currentItem!!.id
                    )
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
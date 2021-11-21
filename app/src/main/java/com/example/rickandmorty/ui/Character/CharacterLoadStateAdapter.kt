package com.example.rickandmorty.ui.Character

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.ui.Episode.EpisodeFragmentDetailsDirections
import com.example.rickandmorty.ui.Location.LocationFragmentDetailsDirections

class CharacterLoadStateAdapter : ListAdapter<Character, CharacterLoadStateAdapter.MyViewHolder>(
    diffCallback
) {

    inner class MyViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        var state: Int = 1
        val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            characterName.text = "${currentItem?.name}"
            characterGender.text = "${currentItem?.gender}"
            characterStatus.text = "${currentItem?.status}"
            characterSpecies.text = "${currentItem?.species}"
            Log.d("AAA", "${currentItem?.episode}")
            val imageLink = currentItem?.image

            characterImage.load(imageLink) {
                crossfade(true)
                crossfade(1000)
            }
            holder.itemView.setOnClickListener {
                val action = when (state) {
                    1 -> {
                        CharactersFragmentDirections.actionCharactersFragmentToCharactersFragmentDetails(
                            currentItem!!.id
                        )
                    }
                    2 -> {
                        EpisodeFragmentDetailsDirections.actionEpisodeFragmentDetailsToCharactersFragmentDetails(
                            currentItem!!.id
                        )
                    }
                    else -> {
                        LocationFragmentDetailsDirections.actionLocationFragmentDetailsToCharactersFragmentDetails(
                            currentItem!!.id
                        )
                    }
                }
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
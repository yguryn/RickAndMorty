package com.example.rickandmorty.ui.Character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.Character

class CharacterAdapter : PagingDataAdapter<Character, CharacterAdapter.MyViewHolder>(diffCallback) {

    inner class MyViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
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
            val imageLink = currentItem?.image

            characterImage.load(imageLink) {
                crossfade(true)
                crossfade(1000)
                scale(Scale.FILL)
            }
            holder.itemView.setOnClickListener {
                val action =
                    CharactersFragmentDirections.actionCharactersFragmentToCharactersFragmentDetails(
                        currentItem!!.id
                    )
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
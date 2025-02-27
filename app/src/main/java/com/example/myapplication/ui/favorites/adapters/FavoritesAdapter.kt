package com.example.myapplication.ui.favorites.adapters

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.databinding.ItemPokemonBinding
import com.example.myapplication.ui.pokemon.interfaces.PokemonAdapterListener
import com.example.myapplication.utils.getInitials
import com.example.myapplication.utils.loadCircularImage

class FavoritesAdapter(): RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var pokemonList = ArrayList<PokemonDetails>()

    inner class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(pokemonList[position]) {
                binding.checkFavorites.setOnCheckedChangeListener(null)
                binding.checkFavorites.visibility = View.GONE

                binding.name.text = name
                binding.altura.text = height.toString()
                binding.peso.text = weight.toString()
                binding.tipo.text = types
                binding.initials.text = name?.getInitials() ?: ""
                binding.numberPokemon.text = String.format(ContextCompat.getString(binding.root.context, R.string.number_pokemon), id)
                sprites?.let {
                    binding.image.loadCircularImage(it) { isLoad ->
                        when(isLoad) {
                            true -> {
                                binding.initials.visibility = View.GONE
                            }
                            false -> {
                                binding.initials.text = name?.getInitials() ?: ""
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPokemon(pokemonList: ArrayList<PokemonDetails>) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }
}
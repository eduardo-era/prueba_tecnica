package com.example.myapplication.ui.pokemon.adapters

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

class PokemonAdapter(private val listener: PokemonAdapterListener): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

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
                binding.checkFavorites.setOnCheckedChangeListener(null)
                binding.checkFavorites.isChecked = isFavorite ?: false

                binding.checkFavorites.setOnCheckedChangeListener { _, isChecked ->
                    listener.onPokemonFavoriteClick(this, isChecked)
                }

                binding.btnSeeDetails.setOnClickListener {
                    listener.onPokemonDetailsClick(this)
                }

                binding.image.setOnClickListener {
                    listener.onPokemonImageClick(this)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPokemon(pokemonList: ArrayList<PokemonDetails>) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePokemon(data: java.util.ArrayList<PokemonDetails>) {
        this.pokemonList.addAll(data)
        notifyDataSetChanged()
    }

    fun setFavorite(pokemonDetails: PokemonDetails, checked: Boolean) {
        val index = pokemonList.indexOfFirst { it.id == pokemonDetails.id }
        if (index != -1) {
            val item = pokemonList[index]
            item.isFavorite = checked
            Handler(Looper.getMainLooper()).post {
                notifyItemChanged(index)
            }
        }
    }
}
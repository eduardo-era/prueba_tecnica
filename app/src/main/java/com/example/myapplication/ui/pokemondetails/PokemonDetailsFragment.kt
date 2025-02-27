package com.example.myapplication.ui.pokemondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.databinding.FragmentPokemonDetailsBinding
import com.example.myapplication.utils.getInitials
import com.example.myapplication.utils.loadCircularImage
import com.google.gson.Gson

class PokemonDetailsFragment: Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    private var pokemonDetails: PokemonDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("pokemonDetailsJson")?.let { json ->
            pokemonDetails = Gson().fromJson(json, PokemonDetails::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clicks()
        setData()
    }

    private fun clicks() {
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_details_to_pokemon)
        }

        binding.checkFavorites.setOnCheckedChangeListener { _, isChecked ->
            setFragmentResult("CHANGE_FAVORITES", bundleOf(
                "data" to Gson().toJson(pokemonDetails),
                "isFavorite" to isChecked
            ))
        }
    }

    private fun setData() {
        binding.initials.text = pokemonDetails?.name?.getInitials()
        binding.name.text = pokemonDetails?.name
        binding.numberPokemon.text = String.format(ContextCompat.getString(binding.root.context, R.string.number_pokemon),
            pokemonDetails?.id ?: ""
        )
        binding.altura.text = pokemonDetails?.height.toString()
        binding.peso.text = pokemonDetails?.weight.toString()
        binding.tipo.text = pokemonDetails?.types.toString()
        binding.checkFavorites.isChecked = pokemonDetails?.isFavorite ?: false
        pokemonDetails?.sprites?.let {
            binding.image.loadCircularImage(it){ isLoad ->
                when(isLoad) {
                    true -> { binding.initials.visibility = View.GONE }
                    false -> { binding.initials.visibility = View.VISIBLE }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
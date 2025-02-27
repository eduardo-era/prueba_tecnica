package com.example.myapplication.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.databinding.FragmentFavoritosBinding
import com.example.myapplication.di.ModuleViewModel
import com.example.myapplication.di.ViewModelFactory
import com.example.myapplication.ui.favorites.adapters.FavoritesAdapter
import com.example.myapplication.ui.pokemon.PokemonUiState
import com.example.myapplication.ui.pokemon.PokemonViewModel
import com.example.myapplication.utils.changeStatusBarColor
import kotlinx.coroutines.launch

class FavoritesFragment: Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FavoritesViewModel> {
        ViewModelFactory(binding.root.context, ModuleViewModel.FAVORITES)
    }

    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        requireActivity().changeStatusBarColor(R.color.red_light, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiState()
        setAdapter()
        clicks()

        viewModel.getFavorites()
    }

    private fun setAdapter() {
        adapter = FavoritesAdapter()
        binding.recyclerPokemon.adapter = adapter
        binding.recyclerPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerPokemon.setHasFixedSize(true)
    }

    private fun clicks() {
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_favorites_to_pokemon)
        }
    }

    private fun initUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                showLoading(state == FavoritesUiState.Loading)
                when(state) {
                    is FavoritesUiState.SetFavoritesList -> { setFavoritesList(state.data) }
                    else -> {}
                }
            }
        }
    }

    private fun setFavoritesList(data: ArrayList<PokemonDetails>) {
        adapter.setPokemon(data)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
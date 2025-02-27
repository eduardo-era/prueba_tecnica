package com.example.myapplication.ui.pokemon

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.databinding.FragmentPokemonBinding
import com.example.myapplication.di.ModuleViewModel
import com.example.myapplication.di.ViewModelFactory
import com.example.myapplication.ui.pokemon.adapters.PokemonAdapter
import com.example.myapplication.ui.pokemon.alerts.PokemonAlerts
import com.example.myapplication.ui.pokemon.interfaces.PokemonAdapterListener
import com.example.myapplication.utils.changeStatusBarColor
import com.google.gson.Gson
import kotlinx.coroutines.launch

class PokemonFragment: Fragment(), PokemonAdapterListener {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PokemonViewModel> {
        ViewModelFactory(binding.root.context, ModuleViewModel.POKEMON)
    }

    private lateinit var adapterPokemon: PokemonAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        requireActivity().changeStatusBarColor(R.color.red_light, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiState()
        setFragmentResults()
        setAdapters()
        clicks()
    }

    private fun clicks() {
        binding.loading.setOnClickListener {}
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_pokemon_to_home)
        }

        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_pkemon_to_favorites)
        }
    }

    private fun setAdapters() {
        adapterPokemon = PokemonAdapter(this)
        binding.recyclerPokemon.adapter = adapterPokemon
        binding.recyclerPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerPokemon.animation = null
        binding.recyclerPokemon.setHasFixedSize(true)
        binding.recyclerPokemon.setItemViewCacheSize(25)
        binding.loading.isEnabled = false

        setRecyclerScrollListener()
    }

    private fun setRecyclerScrollListener() {
        binding.recyclerPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    viewModel.getPokemonList(true)
                }
            }
        })
    }

    private fun initUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                showLoading(state == PokemonUiState.Loading)
                when(state) {
                    is PokemonUiState.Start -> { viewModel.getPokemonList(false) }
                    is PokemonUiState.SetPokemonList -> { setPokemonList(state.data) }
                    is PokemonUiState.UpdatePokemonList -> { updatePokemonList(state.data) }
                    is PokemonUiState.Error -> { showAlertErrorGetPokemon(state.update) }
                    else -> {}
                }
            }
        }
    }

    private fun setPokemonList(data: ArrayList<PokemonDetails>) {
        adapterPokemon.setPokemon(data)
    }

    private fun updatePokemonList(data: ArrayList<PokemonDetails>) {
        adapterPokemon.updatePokemon(data)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    override fun onPokemonDetailsClick(pokemonDetails: PokemonDetails) {
        val bundle = Bundle().apply { putString("pokemonDetailsJson", Gson().toJson(pokemonDetails)) }
        findNavController().navigate(R.id.action_pkemon_to_details, bundle)
    }

    override fun onPokemonImageClick(pokemonDetails: PokemonDetails) {
        PokemonAlerts().showImageAlert(requireActivity(), pokemonDetails)
    }

    override fun onPokemonFavoriteClick(pokemonDetails: PokemonDetails, checked: Boolean) {
        adapterPokemon.setFavorite(pokemonDetails, checked)
        viewModel.setFavorite(pokemonDetails, checked)
    }

    private fun setFragmentResults() {
        parentFragmentManager.setFragmentResultListener("CHANGE_FAVORITES", this) { _, bundle ->
            val dataRaw = bundle.getString("data") ?: ""
            val isConfirmed = bundle.getBoolean("isFavorite", false)
            if (dataRaw.isNotEmpty()) {
                val data = Gson().fromJson(dataRaw, PokemonDetails::class.java)
                Handler(Looper.getMainLooper()).postDelayed({
                    onPokemonFavoriteClick(data, isConfirmed)
                },100)
            }
        }
    }

    private fun showAlertErrorGetPokemon(update: Boolean) {
        PokemonAlerts().showAlertErrorGetPokemon(requireActivity()) {
            when(it) {
                true -> { viewModel.getPokemonList(update) }
                false -> { }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.myapplication.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataResult
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.domain.GetPokemonListUseCase
import com.example.myapplication.domain.SetFavoritePokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val provideGetPokemonListUseCase: GetPokemonListUseCase,
    private val provideSetFavoritePokemonUseCase: SetFavoritePokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Start)
    val uiState = _uiState.asStateFlow()

    fun getPokemonList(isUpdate: Boolean) {
        viewModelScope.launch {
            provideGetPokemonListUseCase.invoke(isUpdate).collect { result ->
                when (result) {
                    is DataResult.Loading -> { _uiState.update { PokemonUiState.Loading } }
                    is DataResult.Success -> {
                        when (isUpdate) {
                            true -> { _uiState.update { PokemonUiState.UpdatePokemonList(result.data) } }
                            false -> { _uiState.update { PokemonUiState.SetPokemonList(result.data) } }
                        }
                    }
                    is DataResult.Error -> { _uiState.update { PokemonUiState.Error(isUpdate) } }
                }
            }
        }
    }

    fun setFavorite(pokemonDetails: PokemonDetails, checked: Boolean) {
        viewModelScope.launch {
            provideSetFavoritePokemonUseCase.invoke(pokemonDetails, checked)
        }
    }

    fun holdState() {
        viewModelScope.launch {
            _uiState.update { PokemonUiState.HideLoading }
        }
    }
}
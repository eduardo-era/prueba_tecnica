package com.example.myapplication.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DataResult
import com.example.myapplication.domain.GetFavoritesPokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(private val provideGetFavoritesPokemonUseCase: GetFavoritesPokemonUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Start)
    val uiState: StateFlow<FavoritesUiState> = _uiState

    fun getFavorites() {
        viewModelScope.launch {
            provideGetFavoritesPokemonUseCase.invoke().collect { result ->
                when (result) {
                    DataResult.Loading -> {_uiState.update { FavoritesUiState.Loading } }
                    is DataResult.Success -> {
                        _uiState.update { FavoritesUiState.SetFavoritesList(result.data) }
                    }

                    is DataResult.Error -> {}
                }
            }
        }
    }
}
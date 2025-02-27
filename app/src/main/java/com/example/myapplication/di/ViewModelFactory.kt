package com.example.myapplication.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.favorites.FavoritesViewModel
import com.example.myapplication.ui.pokemon.PokemonViewModel

class ViewModelFactory(private val context: Context, private val moduleViewmodel: ModuleViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(moduleViewmodel.clazz)) throw Exception()
        val appContext = context.applicationContext
        return when (moduleViewmodel) {
            ModuleViewModel.POKEMON -> ModuleViewModelProvider.providePokemonViewModel(appContext)
            ModuleViewModel.FAVORITES -> ModuleViewModelProvider.provideFavoritesViewModel(appContext)
        } as T
    }
}

enum class ModuleViewModel(val clazz: Class<*>) {
    POKEMON(PokemonViewModel::class.java),
    FAVORITES(FavoritesViewModel::class.java)
}

package com.example.myapplication.ui.pokemon.alerts

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.databinding.AlertFailGetPokemonBinding
import com.example.myapplication.databinding.AlertViewImageBinding
import com.example.myapplication.utils.getInitials
import com.example.myapplication.utils.loadCircularImage

class PokemonAlerts {
    @Suppress("DEPRECATION")

    private val Activity.displayMetrics: DisplayMetrics
        get() {
            val displayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= 30) {
                display?.apply {
                    getRealMetrics(displayMetrics)
                }
            } else {
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }
            return displayMetrics
        }

    fun showImageAlert(activity: Activity, pokemonDetails: PokemonDetails) {
        val binding: AlertViewImageBinding = AlertViewImageBinding.inflate(LayoutInflater.from(activity))
        val dialog = AlertDialog.Builder(activity)

        dialog.setView(binding.root)

        val alertDialog = dialog.create()

        binding.initials.text = pokemonDetails.name?.getInitials() ?: ""

        pokemonDetails.sprites?.let {
            binding.image.loadCircularImage(it) { isLoaded ->
                when (isLoaded) {
                    true -> { binding.initials.visibility = View.GONE }
                    false -> { binding.initials.visibility = View.VISIBLE }
                }
            }
        }

        binding.close.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
        alertDialog.setCancelable(false)
        alertDialog.window?.setLayout(activity.displayMetrics.widthPixels - 200, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun showAlertErrorGetPokemon(activity: FragmentActivity, callback: (Boolean) -> Unit) {
        val binding: AlertFailGetPokemonBinding = AlertFailGetPokemonBinding.inflate(LayoutInflater.from(activity))
        val dialog = AlertDialog.Builder(activity)

        dialog.setView(binding.root)

        val alertDialog = dialog.create()

        binding.btnReintentar.setOnClickListener {
            alertDialog.dismiss()
            callback(true)
        }

        binding.btnCerrar.setOnClickListener {
            alertDialog.dismiss()
            callback(false)
        }

        alertDialog.show()
        alertDialog.setCancelable(false)
        alertDialog.window?.setLayout(activity.displayMetrics.widthPixels - 200, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
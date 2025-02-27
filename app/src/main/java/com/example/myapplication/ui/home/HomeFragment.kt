package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.utils.changeStatusBarColor

class HomeFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        requireActivity().changeStatusBarColor(R.color.black, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clicks()
    }

    private fun clicks() {
        binding.goToInitials.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_initials)
        }

        binding.goToPokemon.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_pokemon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
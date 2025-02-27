package com.example.myapplication.ui.initials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentInitialsBinding
import com.example.myapplication.utils.changeStatusBarColor
import com.example.myapplication.utils.getInitials
import com.example.myapplication.utils.loadCircularImage

class InitialsFragment: Fragment() {

    private var _binding: FragmentInitialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInitialsBinding.inflate(inflater, container, false)
        requireActivity().changeStatusBarColor(R.color.green, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clicks()
    }

    private fun clicks() {
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_initials_to_home)
        }

        binding.btnApply.setOnClickListener {
            validateFields()
        }

        binding.example.setOnClickListener {
            showEjemplo()
        }
    }

    private fun validateFields() {
        val url = binding.etUserPhoto.text.toString()
        val name = binding.etUserName.text.toString()
        if (url.isNotEmpty() && name.isNotEmpty() && url.contains("http")) {
            binding.userPhoto.loadCircularImage(url) {
                if (it) {
                    binding.initials.text = name.getInitials()
                    binding.initials.visibility = View.INVISIBLE
                    binding.userPhoto.visibility = View.VISIBLE

                } else {
                    binding.initials.visibility = View.VISIBLE
                    binding.initials.text = name.getInitials()
                    binding.userPhoto.visibility = View.GONE
                }
            }

        } else {
            Toast.makeText(requireContext(), ContextCompat.getString(requireContext(), R.string.all_fields_required), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEjemplo() {
        val popupMenu = PopupMenu(requireContext(), binding.example)
        requireActivity().menuInflater.inflate(R.menu.menu_ejemplos_iniciales, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.tanjiro -> {
                    binding.etUserName.setText(ContextCompat.getString(requireContext(), R.string.tanjiro))
                    binding.etUserPhoto.setText(ContextCompat.getString(requireContext(), R.string.tanjiro_photo))
                }
                R.id.luffy -> {
                    binding.etUserName.setText(ContextCompat.getString(requireContext(), R.string.luffy))
                    binding.etUserPhoto.setText(ContextCompat.getString(requireContext(), R.string.luffy_photo))
                }
                R.id.spike -> {
                    binding.etUserName.setText(ContextCompat.getString(requireContext(), R.string.spike))
                    binding.etUserPhoto.setText(ContextCompat.getString(requireContext(), R.string.spike_photo))
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
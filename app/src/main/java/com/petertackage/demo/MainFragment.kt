package com.petertackage.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.petertackage.demo.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    // Using technique from https://developer.android.com/topic/libraries/view-binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMainFibonacci.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_fibonacciFragment) }
        binding.buttonMainMeme.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_memeFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        // TODO Use ViewModel
    }

}
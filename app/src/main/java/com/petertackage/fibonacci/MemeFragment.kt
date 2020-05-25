package com.petertackage.fibonacci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.petertackage.fibonacci.databinding.FragmentMemeBinding

class MemeFragment : Fragment() {

    // Using technique from https://developer.android.com/topic/libraries/view-binding
    private var _binding: FragmentMemeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel: MockingMemeFragmentViewModel by viewModels()

        viewModel.mockingMemeText
            .observe(viewLifecycleOwner,
                Observer { text -> binding.textviewMeme.text = text })
    }

}

package com.petertackage.demo.meme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.petertackage.demo.databinding.FragmentMemeBinding

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

        binding.edittextMemeInput.doOnTextChanged { text, _, _, _ ->
            text?.let { viewModel.toMockingMeme(it.toString()) }
        }

        viewModel.mockingMemeText
            .observe(viewLifecycleOwner,
                Observer { text -> binding.textviewMemeOutput.text = text })
    }

}

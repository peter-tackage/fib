package com.petertackage.fibonacci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.petertackage.fibonacci.databinding.FragmentFibonacciBinding

class FibonacciFragment : Fragment() {

    // Using technique from https://developer.android.com/topic/libraries/view-binding
    private var _binding: FragmentFibonacciBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFibonacciBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel: FibonacciFragmentViewModel by viewModels()

        viewModel.sequence
            .observe(viewLifecycleOwner,
                Observer { sequence ->
                    when (sequence) {
                        is FibonacciSequence.Streaming -> binding.textviewFibonacciValues.text =
                            sequence.values.joinToString(separator = "\n")
                        is FibonacciSequence.Completed -> binding.textviewFibonacciValues.append("\nDone!")
                    }
                })
    }

}

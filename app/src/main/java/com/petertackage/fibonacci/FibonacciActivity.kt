package com.petertackage.fibonacci

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.petertackage.fibonacci.databinding.ActivityFibonacciBinding

class FibonacciActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFibonacciBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel: FibonacciActivityViewModel by viewModels()

        viewModel.sequence
            .observe(this,
                Observer { sequence ->
                    when (sequence) {
                        is FibonacciSequence.Streaming -> binding.textviewFibonacciValues.text =
                            sequence.values.joinToString(separator = "\n")
                        is FibonacciSequence.Completed -> binding.textviewFibonacciValues.append("\nDone!")
                    }
                })
    }

}

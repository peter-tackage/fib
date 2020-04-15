package com.petertackage.fibonacci

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.petertackage.fibonacci.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val model: MainActivityViewModel by viewModels()

        model.sequence
            .observe(this,
                Observer { values ->
                    binding.textviewMainFibonacci.text =
                        values.joinToString(separator = "\n")
                })
    }

}
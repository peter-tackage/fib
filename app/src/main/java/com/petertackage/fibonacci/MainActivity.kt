package com.petertackage.fibonacci

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var fibonacciTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fibonacciTextView = findViewById(R.id.textview_main_fibonacci)
        val model: MainActivityViewModel by viewModels()

        model.getSequence()
            .observe(this,
                Observer { values ->
                    fibonacciTextView.text =
                        values.joinToString(separator = "\n")
                })
    }

}
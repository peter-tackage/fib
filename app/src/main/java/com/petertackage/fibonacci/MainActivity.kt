package com.petertackage.fibonacci

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var fibonacciTextView: TextView
    private lateinit var fibonacciGenerator: FibonacciGenerator
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fibonacciTextView = findViewById(R.id.textview_main_fibonacci)
        fibonacciGenerator = FibonacciGenerator()
    }

    override fun onStart() {
        super.onStart()
        job = GlobalScope.launch {
            var index = 0
            while (true) {
                val result = fibonacciGenerator.calculate(index++)
                if (result in 0..Long.MAX_VALUE) {
                    withContext(Dispatchers.Main) {
                        fibonacciTextView.append(result.toString().plus("\n"))
                    }
                } else {
                    fibonacciTextView.append("Done.")
                    break
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

}
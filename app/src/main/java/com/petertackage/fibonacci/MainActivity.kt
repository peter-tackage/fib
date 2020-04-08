package com.petertackage.fibonacci

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var fibonacciTextView: TextView
    private lateinit var fibonacciGenerator: FibonacciGenerator
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fibonacciTextView = findViewById(R.id.textview_main_fibonacci)
        fibonacciGenerator = FibonacciGenerator()
    }

    override fun onStart() {
        super.onStart()
        job = displayFibonacciSequence()
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }

    private fun displayFibonacciSequence(): Job =
            GlobalScope.launch {
                var position = 0
                while (true) {
                    try {
                        val result = fibonacciGenerator.calculate(position++)
                        withContext(Dispatchers.Main) {
                            fibonacciTextView.append(result.toString().plus("\n"))
                        }
                    } catch (exp: ArithmeticException) {
                        withContext(Dispatchers.Main) {
                            fibonacciTextView.append("Done.")
                        }
                        break
                    }
                    delay(TESTING_DELAY_MILLIS)
                }
            }

    private companion object {
        const val TESTING_DELAY_MILLIS: Long = 0
    }
}
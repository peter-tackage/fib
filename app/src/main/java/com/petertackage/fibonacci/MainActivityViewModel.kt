package com.petertackage.fibonacci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val fibonacciGenerator: FibonacciGenerator = FibonacciGenerator(),
    seed: Int = 0,
    private val testingDelayMillis: Long = 250
) : ViewModel() {

    private val sequence: MutableLiveData<List<Long>> = MutableLiveData()
    private val job: Job

    init {
        job = generateFibonacciSequence(seed)
    }

    fun getSequence(): LiveData<List<Long>> = sequence

    private fun generateFibonacciSequence(seed: Int): Job =
        GlobalScope.launch {
            var position = seed
            val values = mutableListOf<Long>()
            while (true) {
                try {
                    val result = fibonacciGenerator.calculate(position++)
                    values+=result
                    sequence.postValue(values.toList())
                } catch (exp: ArithmeticException) {
                    break
                }
                delay(testingDelayMillis)
            }
        }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}
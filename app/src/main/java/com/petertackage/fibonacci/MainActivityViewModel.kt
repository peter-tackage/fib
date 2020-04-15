package com.petertackage.fibonacci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val fibonacciGenerator: FibonacciGenerator = FibonacciGenerator(),
    seed: Int = 0,
    private val testingDelayMillis: Long = 0
) : ViewModel() {

    private val mutableSequence = MutableLiveData<List<Long>>()

    val sequence = mutableSequence as LiveData<List<Long>>

    init {
        viewModelScope.launch { generateFibonacciSequence(seed) }
    }

    private suspend fun generateFibonacciSequence(seed: Int) =
        withContext(Dispatchers.Default) {
            var position = seed
            val values = mutableListOf<Long>()
            while (true) {
                try {
                    val result = fibonacciGenerator.calculate(position++)
                    values += result
                    // toList() gives us a shallow copy which should be enough, because Longs are immutable.
                    mutableSequence.postValue(values.toList())
                } catch (exp: ArithmeticException) {
                    break
                }
                // delay(testingDelayMillis)
            }
        }

}
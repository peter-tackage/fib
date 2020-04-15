package com.petertackage.fibonacci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainActivityViewModel(
    private val fibonacciGenerator: FibonacciGenerator = FibonacciGenerator(),
    seed: Int = 0,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val testingDelayMillis: Long = 100
) : ViewModel() {

    private val mutableSequence = MutableLiveData<List<Long>>()

    val sequence = mutableSequence as LiveData<List<Long>>

    init {
        // Launch creates a new coroutine in the ViewModel scope (automatically cancelled).
        viewModelScope.launch(defaultDispatcher) { generateFibonacciSequence(seed) }
    }

    // - To be able to access isActive, make this an extension - alternative is to explicitly use
    //   withContext(defaultDispatcher) { // impl }.
    // - Is a suspend function, as calls delay.
    private suspend fun CoroutineScope.generateFibonacciSequence(seed: Int) {
        var position = seed
        val values = mutableListOf<Long>()
        while (isActive) {
            try {
                val result = fibonacciGenerator.calculate(position++)
                values += result
                // toList() gives us a shallow copy which should be enough, because Longs are immutable.
                // Use postValue as we are on a background thread.
                mutableSequence.postValue(values.toList())
            } catch (exp: ArithmeticException) {
                break
            }
            delay(testingDelayMillis)
        }
    }

}

package com.petertackage.fibonacci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FibonacciFragmentViewModel(
    private val fibonacciGenerator: FibonacciGenerator = FibonacciGenerator(),
    seed: Int = 0,
    dispatcherProvider: CoroutineDispatcherProvider = CoroutineDispatcherProvider(),
    private val testingDelayMillis: Long = 200
) : ViewModel() {

    private val _sequence = MutableLiveData<FibonacciSequence>()
    val sequence: LiveData<FibonacciSequence> get() = _sequence

    init {
        // `launch` creates a new coroutine in the ViewModel scope (automatically cancelled on VM lifecycle).
        // We inject the dispatcher for testing purposes, although from my experimentation with
        // using testRunBlocking.testDispatcher, the testDispatching context seems to be naturally
        // inherited from the scope.
        //
        // Using this type of injection likely makes more sense when there are different Dispatchers
        // in use, and we want to individually control them.
        viewModelScope.launch(dispatcherProvider.computation) { generateFibonacciSequence(seed) }
    }

    // - Is an extension to be able to access `isActive` to immediately cancel the work.
    // - The alternative is to explicitly use `withContext(defaultDispatcher) { // impl }` to launch,
    //   but I felt that this reads better.
    // - Is a suspend function as calls it `delay`.
    //
    // FIXME - If I replace `isActive` with `true`, then the test still passes! (?)
    //  - Is the `isActive` check actually necessary?
    //  - When I removed the `delay` and it's no longer a suspending fun, then even with the `isActive`
    //    it still loops too much. ??
    // - TODO I need to understand what is happening here.
    //
    private suspend fun CoroutineScope.generateFibonacciSequence(seed: Int) {
        var position = seed
        val values = mutableListOf<Long>()
        while (isActive) {
            try {
                val result = fibonacciGenerator.calculate(position++)
                values += result
                // toList() gives us a shallow copy which should be enough, because Longs are immutable.
                // Use postValue as we are on a background thread.
                _sequence.postValue(FibonacciSequence.Streaming(values.toList()))
            } catch (exp: ArithmeticException) {
                _sequence.postValue(FibonacciSequence.Completed(values))
                break
            }
            delay(testingDelayMillis)
        }
    }

}

// Probably an unnecessary use of sealed class.
// Using Completed with a value is essentially a crude "hot stream" which caches the fibonacci
//   sequence calculation result.
// If we only emitted the *event* then we would rely on the View retaining the previously emitted
//   values on configuration change.
sealed class FibonacciSequence {
    data class Streaming(val values: List<Long>) : FibonacciSequence()
    data class Completed(val values: List<Long>) : FibonacciSequence()
}

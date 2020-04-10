package com.petertackage.fibonacci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private val numberStream: MutableLiveData<Long> = MutableLiveData<Long>()
    private val sequence: LiveData<List<Long>>
    private val job: Job

    init {
        sequence = numberStream.scan(listOf(),
            { existing, new ->
                existing.toMutableList().apply { add(new) }.toList()
            }) // FIXME There must be a better way
        job = generateFibonacciSequence(seed)
    }

    fun getSequence(): LiveData<List<Long>> = sequence

    private fun generateFibonacciSequence(seed: Int): Job =
        GlobalScope.launch {
            var position = seed
            while (true) {
                try {
                    val result = fibonacciGenerator.calculate(position++)
                    numberStream.postValue(result)
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

    // https://github.com/adibfara/Lives
    fun <T, R> LiveData<T>.scan(
        initialSeed: R,
        accumulator: (accumulated: R, currentValue: T) -> R
    ): MutableLiveData<R> {
        var accumulatedValue = initialSeed
        return MediatorLiveData<R>().apply {
            value = initialSeed
            addSource(this@scan) { emittedValue ->
                accumulatedValue = accumulator(accumulatedValue, emittedValue!!)
                value = accumulatedValue
            }
        }
    }

}
package com.petertackage.fibonacci

class FibonacciGenerator(
    private val calculated: MutableMap<Int, Long>
    = mutableMapOf(0 to 0L, 1 to 1L)
) {

    fun calculate(n: Int): Long {
      //  Log.d("dfdf", n.toString())
        require(n >= 0) { "Fibonacci position must be non-negative, was: $n" }
        return existing(n) ?: calculateAndSet(n)
    }

    private fun existing(n: Int) = calculated[n]

    private fun calculateAndSet(n: Int): Long {
        val result = calculate(n - 1) + calculate(n - 2)
        calculated[n] = result
        return result
    }

}

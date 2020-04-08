package com.petertackage.fibonacci

class FibonacciGenerator(
    private val calculated: MutableMap<Int, Long>
    = mutableMapOf(0 to 0L, 1 to 1L)
) {

    fun calculate(position: Int): Long {
        require(position >= 0) { "Fibonacci position must be non-negative, was: $position" }
        return existing(position) ?: calculateAndSet(position)
    }

    private fun existing(position: Int) = calculated[position]

    private fun calculateAndSet(position: Int): Long {
        // Use addExact to detect arithmetic overflow in calculation
        val result = Math.addExact(calculate(position - 1), calculate(position - 2))
        calculated[position] = result
        return result
    }

}

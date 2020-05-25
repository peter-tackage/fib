package com.petertackage.demo.fibonacci

class FibonacciGenerator(
    private val calculated: MutableMap<Int, Long> = mutableMapOf()
) {

    fun calculate(position: Int): Long {
        require(position >= 0) { "Fibonacci position must be non-negative, was: $position" }
        return when (position) {
            0 -> 0
            1 -> 1
            else -> precalculated(position) ?: calculateAndSet(position)
        }
    }

    private fun precalculated(position: Int) = calculated[position]

    private fun calculateAndSet(position: Int): Long {
        // Use addExact to detect arithmetic overflow in calculation
        return Math.addExact(calculate(position - 1), calculate(position - 2))
            .also { calculated[position] = it }
    }
}

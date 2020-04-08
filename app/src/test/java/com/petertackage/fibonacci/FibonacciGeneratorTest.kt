package com.petertackage.fibonacci

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FibonacciGeneratorTest {

    private lateinit var fibonacciGenerator: FibonacciGenerator

    @Before
    fun setUp() {
        fibonacciGenerator = FibonacciGenerator()
    }

    @Test
    fun `fib returns 0 for 0`() {
        assertThat(fibonacciGenerator.calculate(0)).isEqualTo(0)
    }

    @Test
    fun `fib returns 1 for 1`() {
        assertThat(fibonacciGenerator.calculate(1)).isEqualTo(1)
    }

    @Test
    fun `fib returns 1 for 2`() {
        assertThat(fibonacciGenerator.calculate(2)).isEqualTo(1)
    }

    @Test
    fun `fib returns 55 for 10`() {
        assertThat(fibonacciGenerator.calculate(10)).isEqualTo(55)
    }
}
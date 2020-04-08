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

    // TODO These could be parameterized tests in JUnit 5.

    @Test
    fun `calculate returns 0 for position 0`() {
        assertThat(fibonacciGenerator.calculate(0)).isEqualTo(0)
    }

    @Test
    fun `calculate returns 1 for position 1`() {
        assertThat(fibonacciGenerator.calculate(1)).isEqualTo(1)
    }

    @Test
    fun `calculate returns 1 for position 2`() {
        assertThat(fibonacciGenerator.calculate(2)).isEqualTo(1)
    }

    @Test
    fun `calculate returns 3 for position 4`() {
        assertThat(fibonacciGenerator.calculate(4)).isEqualTo(3)
    }

    @Test
    fun `calculate returns 55 for position 10`() {
        assertThat(fibonacciGenerator.calculate(10)).isEqualTo(55)
    }
}
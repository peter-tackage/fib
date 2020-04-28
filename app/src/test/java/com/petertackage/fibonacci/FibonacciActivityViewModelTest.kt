package com.petertackage.fibonacci

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import com.petertackage.livedatatest.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FibonacciActivityViewModelTest {
    companion object {
        const val TICK_DURATION_MS: Long = 1_500
    }

    @get:Rule
    val liveDataMainThreadRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = TestDispatcherRule()

    private lateinit var viewModel: FibonacciActivityViewModel

    @Before
    fun setUp() {
        viewModel = FibonacciActivityViewModel(
            dispatcherProvider = provideTestCoroutineDispatcherProvider(coroutinesTestRule.testDispatcher),
            testingDelayMillis = TICK_DURATION_MS
        )
    }

    @Test
    fun `fibonacci streams sequence`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val observer = viewModel.sequence.test()

            // expect a single value to have been emitted - which is a list with a single entry
            assertThat(observer.value)
                .isEqualTo(FibonacciSequence.Streaming(listOf(0))) // initial value

            advanceTimeBy(TICK_DURATION_MS)
            assertThat(observer.values)
                .containsExactly(
                    FibonacciSequence.Streaming(listOf(0)),
                    FibonacciSequence.Streaming(listOf(0, 1))
                )

            advanceTimeBy(TICK_DURATION_MS)
            assertThat(observer.values)
                .containsExactly(
                    FibonacciSequence.Streaming(listOf(0)),
                    FibonacciSequence.Streaming(listOf(0, 1)),
                    FibonacciSequence.Streaming(listOf(0, 1, 1))
                )
        }

    @Test
    fun `fibonacci sequence calculates all elements`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            advanceUntilIdle()
            assertThat(viewModel.sequence.value)
                .isInstanceOfSatisfying(FibonacciSequence.Completed::class.java)
                {
                    assertThat(it.values)
                        .startsWith(0L, 1L, 1L, 2L, 3L, 5L)
                        .endsWith(7540113804746346429L)
                        .hasSize(93)
                }
        }

    @Test
    fun `clearing ViewModel cancels sequence stream`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val observer = viewModel.sequence.test()

            viewModel.clear() // simulates ViewModel teardown
            advanceUntilIdle()

            assertThat(observer.values)
                .containsExactly(FibonacciSequence.Streaming(listOf(0))) // initial value
        }

}

private fun ViewModel.clear() {
    ViewModel::class.java.getDeclaredMethod("clear")
        .apply { isAccessible = true }(this)
}
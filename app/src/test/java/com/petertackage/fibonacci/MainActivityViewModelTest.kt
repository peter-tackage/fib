package com.petertackage.fibonacci

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petertackage.livedatatest.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    companion object {
        const val TESTING_DELAY: Long = 50
    }

    @get:Rule
    val liveDataMainThreadRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = TestDispatcherRule()

    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        viewModel = MainActivityViewModel(
            defaultDispatcher = coroutinesTestRule.testDispatcher,
            testingDelayMillis = TESTING_DELAY
        )
    }

    @Test
    fun `fibonacci streams sequence`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val observer = viewModel.sequence.test()
            assertThat(observer.values).containsExactly(listOf(0)) // sanity check

            advanceTimeBy(3 * TESTING_DELAY)

            assertThat(observer.values)
                .containsExactly(
                    listOf(0),
                    listOf(0, 1),
                    listOf(0, 1, 1),
                    listOf(0, 1, 1, 2)
                )

            advanceTimeBy(TESTING_DELAY)

            assertThat(observer.values)
                .containsExactly(
                    listOf(0),
                    listOf(0, 1),
                    listOf(0, 1, 1),
                    listOf(0, 1, 1, 2),
                    listOf(0, 1, 1, 2, 3)
                )
        }

    @Test
    fun `fibonacci sequence calculates all elements`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            advanceUntilIdle()
            assertThat(viewModel.sequence.value)
                .startsWith(0L, 1L, 1L, 2L, 3L, 5L)
                .endsWith(7540113804746346429L)
                .hasSize(93)
        }

}
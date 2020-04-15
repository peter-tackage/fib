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
        const val TICK_DURATION_MS: Long = 50
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
            testingDelayMillis = TICK_DURATION_MS
        )
    }

    @Test
    fun `fibonacci streams sequence`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val observer = viewModel.sequence.test()

            assertThat(observer.values).containsExactly(listOf(0)) // initial value

            advanceTimeBy(TICK_DURATION_MS)
            assertThat(observer.values)
                .containsExactly(
                    listOf(0),
                    listOf(0, 1)
                )

            advanceTimeBy(TICK_DURATION_MS)
            assertThat(observer.values)
                .containsExactly(
                    listOf(0),
                    listOf(0, 1),
                    listOf(0, 1, 1)
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
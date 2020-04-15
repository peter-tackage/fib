package com.petertackage.fibonacci

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    @get:Rule
    val liveDataMainThreadRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = TestDispatcherRule()

    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        viewModel = MainActivityViewModel(defaultDispatcher = coroutinesTestRule.testDispatcher)
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
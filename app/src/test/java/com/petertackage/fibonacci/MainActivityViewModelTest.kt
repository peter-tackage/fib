package com.petertackage.fibonacci

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val liveDataMainThreadRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = TestDispatcherRule(testDispatcher)

    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        viewModel = MainActivityViewModel(dispatcher = testDispatcher)
    }

    @Test
    fun `fibonacci sequence is not empty`() = testDispatcher.runBlockingTest {
        advanceUntilIdle()
        assertThat(viewModel.sequence.value).hasSize(93)
    }

}
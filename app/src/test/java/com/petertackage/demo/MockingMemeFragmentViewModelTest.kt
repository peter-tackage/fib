package com.petertackage.demo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petertackage.livedatatest.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MockingMemeFragmentViewModelTest {

    @get:Rule
    val liveDataMainThreadRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = TestDispatcherRule()

    private lateinit var viewModel: MockingMemeFragmentViewModel

    @Before
    fun setUp() {
        viewModel = MockingMemeFragmentViewModel(
            defaultDispatcher = coroutinesTestRule.testDispatcher
        )
    }

    @Test
    fun `converts to mocking meme`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val text = "This is a really good piece of text"
            val observer = viewModel.mockingMemeText.test()

            viewModel.toMockingMeme(text)

            assertThat(observer.value)
                .isNotEqualTo(text)
                .containsIgnoringCase(text)
                .also { println(observer.value) }
        }

}
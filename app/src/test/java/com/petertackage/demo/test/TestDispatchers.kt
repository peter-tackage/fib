package com.petertackage.demo.test

import com.petertackage.demo.CoroutineDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
fun provideTestCoroutineDispatcherProvider(testDispatcher: TestCoroutineDispatcher): CoroutineDispatcherProvider {
    return CoroutineDispatcherProvider(
        main = testDispatcher,
        io = testDispatcher,
        computation = testDispatcher
    )
}
package com.petertackage.fibonacci

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
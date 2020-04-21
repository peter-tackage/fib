package com.petertackage.fibonacci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

class MockingMemeFragmentViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var job: Job? = null

    fun toMockingMeme(text: String) {
        // Meant to work like a switch map - discard any conversion that is in progress
        job?.cancel()
        job = viewModelScope.launch(defaultDispatcher) {
            val sarcasm = toReallyGoodSarcasticText(text)
            mutableSarcasticText.postValue(sarcasm)
        }
    }

    private val mutableSarcasticText = MutableLiveData<String>()
    val mockingMeme = mutableSarcasticText as LiveData<String>

    private fun toReallyGoodSarcasticText(input: String): String {
        val atRate = 50
        return input
            .map { it.randomizeCase(atRate) }
            .joinToString("")
    }

    private fun Char.randomizeCase(atRate: Int) =
        if (shouldCapitalize(atRate)) toUpperCase() else toLowerCase()

    private fun shouldCapitalize(atRate: Int) = Random.nextInt(100) < atRate

}

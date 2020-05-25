package com.petertackage.demo

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
    defaultConfig: Config = Config(),
    defaultText: String = "",
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var job: Job? = null

    fun toMockingMeme(text: String) {
        // Meant to work like a switch map - discard any conversion that is in progress
        job?.cancel() // TODO Determine if this would cancel *all* jobs started in the scope
        job = viewModelScope.launch(defaultDispatcher) {
            val mockingText = toReallySmartSoundingMemeText(text)
            _mockingMemeText.postValue(mockingText)
        }
    }

    private val _mockingMemeText = MutableLiveData(defaultText)
    val mockingMemeText: LiveData<String> get() = _mockingMemeText

    private val _config: MutableLiveData<Config> = MutableLiveData(defaultConfig)
    val config: LiveData<Config> get() = _config

    private fun toReallySmartSoundingMemeText(text: String): String {
        return text
            .map { it.randomizeCase(_config.value!!) }
            .joinToString("")
    }

    private fun Char.randomizeCase(config: Config): Char =
        if (shouldCapitalize(config)) toUpperCase() else toLowerCase()

    private fun shouldCapitalize(config: Config) =
        Random.nextInt(100) < config.rate.percent

    // Rules:
    // - Every single character word should be reversed (add option)
    // - Every word must have a mixture (add option)
    // - No more than two consecutive characters should have the same case (add option)

    enum class Rate(val percent: Int) {
        NONE(0),
        LOW(25),
        MEDIUM(50),
        HIGH(75),
        ALL(100)
    }

    data class Config(
        val rate: Rate = Rate.MEDIUM,
        val maxConsecutiveChars: Int = 2,
        val alwaysInvertSingleCharWords: Boolean = true
    )
}

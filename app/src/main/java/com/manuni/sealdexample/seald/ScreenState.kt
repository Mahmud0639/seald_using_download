package com.manuni.sealdexample.seald

sealed class ScreenState {
    object Loading: ScreenState()
    data class Success(val quote: String): ScreenState()
    data class Error(val message: String): ScreenState()
}
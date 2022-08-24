package com.example.utvstories.util

sealed class State {
    object Loading : State()
    object Success : State()
    data class Error(val e: Exception, val message: String) : State()
}

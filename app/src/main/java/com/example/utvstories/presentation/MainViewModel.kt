package com.example.utvstories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utvstories.domain.GetStoriesWithDateUseCase
import com.example.utvstories.entity.Story
import com.example.utvstories.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetStoriesWithDateUseCase
): ViewModel() {

    private val _storiesList = MutableStateFlow<List<Story>>(emptyList())
    val storiesList = _storiesList.asStateFlow()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        fetchStories()
    }

    fun fetchStories() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                _storiesList.value = useCase.execute()
                _state.value = State.Success
            }
            catch (e: Exception) {
                _storiesList.value = mutableListOf()
                _state.value = State.Error(e, e.message ?: "Unknown exception")
            }
        }
    }
}
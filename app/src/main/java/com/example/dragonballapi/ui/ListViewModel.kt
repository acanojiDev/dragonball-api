package com.example.dragonballapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballapi.data.model.Personaje
import com.example.dragonballapi.data.repository.personaje.PersonajeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PersonajeListUiState(
    val isLoading: Boolean = true,
    val personajeList: List<Personaje> = emptyList(),
    val isSyncing: Boolean = false,
    val syncError: String? = null
)

@HiltViewModel
class PersonajeListViewModel @Inject constructor(
    private val repository: PersonajeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonajeListUiState())
    val uiState: StateFlow<PersonajeListUiState> = _uiState.asStateFlow()

    init {
        loadFromLocal()
        syncWithNetwork()
    }

    private fun loadFromLocal() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                repository.observeAllPersonaje()
                    .collect { result ->
                        result.onSuccess { personajeList ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                personajeList = personajeList
                            )
                        }
                        result.onFailure { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                syncError = error.message ?: "Error desconocido"
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    syncError = e.message ?: "Error desconocido"
                )
            }
        }
    }

    private fun syncWithNetwork() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSyncing = true)

            try {
                repository.syncPersonajeFromNetwork()
                _uiState.value = _uiState.value.copy(isSyncing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSyncing = false,
                    syncError = e.message ?: "Error al sincronizar"
                )
            }
        }
    }
}
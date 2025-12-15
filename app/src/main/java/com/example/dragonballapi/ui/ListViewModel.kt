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
    val isLoading: Boolean = true,      // ¿Está cargando?
    val personajeList: List<Personaje> = emptyList(),  // Los datos
    val isSyncing: Boolean = false,     // ¿Sincronizando?
    val syncError: String? = null       // ¿Error de sync?
)

@HiltViewModel
class PersonajeListViewModel @Inject constructor(
    private val repository: PersonajeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonajeListUiState())
    val uiState: StateFlow<PersonajeListUiState> = _uiState.asStateFlow()

    init {
        loadFromLocal()     // 1. Cargar de BD
        syncWithNetwork()   // 2. Sincronizar en background
    }

    private fun loadFromLocal() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.observeAllPersonaje()
                .collect { result ->
                    result.onSuccess { personajeList ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            personajeList = personajeList  // UI se redibuja
                        )
                    }
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
                    syncError = "Error"
                )
            }
        }
    }
}
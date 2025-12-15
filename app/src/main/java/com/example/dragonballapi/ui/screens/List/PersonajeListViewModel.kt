package com.example.dragonballapi.ui.screens.List

import android.util.Log
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

    companion object {
        private const val TAG = "PersonajeListViewModel"
    }

    private val _uiState = MutableStateFlow(PersonajeListUiState())
    val uiState: StateFlow<PersonajeListUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel inicializado")
        // Primero sincronizar, LUEGO observar
        syncWithNetwork()
    }

    /**
     * Sincroniza datos desde la API PRIMERO
     * Luego comienza a observar la BD
     */
    private fun syncWithNetwork() {
        Log.d(TAG, "syncWithNetwork() iniciando")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSyncing = true, isLoading = true)

            try {
                repository.syncPersonajeFromNetwork()
                Log.d(TAG, "✅ Sincronización exitosa")
                _uiState.value = _uiState.value.copy(isSyncing = false)

                // Después de sincronizar, observa los cambios
                loadFromLocal()

            } catch (e: Exception) {
                Log.e(TAG, "❌ Error al sincronizar", e)
                _uiState.value = _uiState.value.copy(
                    isSyncing = false,
                    isLoading = false,
                    syncError = "Error: ${e.message}"
                )
                // Aun así intenta cargar lo que hay en BD local
                loadFromLocal()
            }
        }
    }

    /**
     * Observa cambios en la BD local
     * Se llama DESPUÉS de syncWithNetwork()
     */
    private fun loadFromLocal() {
        Log.d(TAG, "loadFromLocal() iniciando")
        viewModelScope.launch {
            repository.observeAllPersonaje()
                .collect { result ->
                    Log.d(TAG, "Result recibido: $result")

                    result.onSuccess { personajeList ->
                        Log.d(TAG, "✅ Éxito: ${personajeList.size} personajes cargados")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            personajeList = personajeList
                        )
                    }

                    result.onFailure { error ->
                        Log.e(TAG, "❌ Error al cargar", error)
                        if (_uiState.value.personajeList.isEmpty()) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                syncError = error.message ?: "Error desconocido"
                            )
                        }
                    }
                }
        }
    }
}
package com.example.dragonballapi.ui.screens

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

// 📊 Estado de la UI
data class PersonajeListUiState(
    val isLoading: Boolean = true,                      // ¿Está cargando?
    val personajeList: List<Personaje> = emptyList(),   // Los datos
    val isSyncing: Boolean = false,                      // ¿Sincronizando?
    val syncError: String? = null                        // ¿Error de sync?
)

@HiltViewModel
class PersonajeListViewModel @Inject constructor(
    private val repository: PersonajeRepository
) : ViewModel() {

    companion object {
        private const val TAG = "PersonajeListViewModel"
    }

    // State reactivo para la UI
    private val _uiState = MutableStateFlow(PersonajeListUiState())
    val uiState: StateFlow<PersonajeListUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel inicializado")
        loadFromLocal()      // 1. Cargar de BD
        syncWithNetwork()    // 2. Sincronizar en background
    }

    /**
     * Carga datos de la BD de forma reactiva
     * Usa Flow para observar cambios en tiempo real
     */
    private fun loadFromLocal() {
        Log.d(TAG, "loadFromLocal() iniciando")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.observeAllPersonaje()
                .collect { result ->
                    Log.d(TAG, "Result recibido: $result")

                    result.onSuccess { personajeList ->
                        Log.d(TAG, "✅ Éxito: ${personajeList.size} personajes cargados")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            personajeList = personajeList  // UI se redibuja
                        )
                    }

                    result.onFailure { error ->
                        Log.e(TAG, "❌ Error al cargar", error)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            syncError = error.message ?: "Error desconocido"
                        )
                    }
                }
        }
    }

    /**
     * Sincroniza datos desde la API
     * Si hay error (sin internet), mantiene los datos locales
     */
    private fun syncWithNetwork() {
        Log.d(TAG, "syncWithNetwork() iniciando")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSyncing = true)

            try {
                repository.syncPersonajeFromNetwork()
                Log.d(TAG, "✅ Sincronización exitosa")
                _uiState.value = _uiState.value.copy(isSyncing = false)
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error al sincronizar", e)
                _uiState.value = _uiState.value.copy(
                    isSyncing = false,
                    syncError = "Error: ${e.message}"
                )
            }
        }
    }

}
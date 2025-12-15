package com.example.dragonballapi.ui.screens.List


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballapi.data.model.Planeta
import com.example.dragonballapi.data.repository.planeta.PlanetaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlanetaListUiState(
    val isLoading: Boolean = true,
    val planetaList: List<Planeta> = emptyList(),
    val isSyncing: Boolean = false,
    val syncError: String? = null
)

@HiltViewModel
class PlanetaListViewModel @Inject constructor(
    private val repository: PlanetaRepository
) : ViewModel() {

    companion object {
        private const val TAG = "PlanetaListViewModel"
    }

    private val _uiState = MutableStateFlow(PlanetaListUiState())
    val uiState: StateFlow<PlanetaListUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel inicializado")
        syncWithNetwork()
    }

    private fun syncWithNetwork() {
        Log.d(TAG, "syncWithNetwork() iniciando")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSyncing = true, isLoading = true)

            try {
                repository.syncPlanetaFromNetwork()
                Log.d(TAG, "✅ Sincronización exitosa")
                _uiState.value = _uiState.value.copy(isSyncing = false)
                loadFromLocal()
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error al sincronizar", e)
                _uiState.value = _uiState.value.copy(
                    isSyncing = false,
                    isLoading = false,
                    syncError = "Error: ${e.message}"
                )
                loadFromLocal()
            }
        }
    }

    private fun loadFromLocal() {
        Log.d(TAG, "loadFromLocal() iniciando")
        viewModelScope.launch {
            repository.observeAllPlaneta()
                .collect { result ->
                    Log.d(TAG, "Result recibido: $result")

                    result.onSuccess { planetaList ->
                        Log.d(TAG, "✅ Éxito: ${planetaList.size} planetas cargados")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            planetaList = planetaList
                        )
                    }

                    result.onFailure { error ->
                        Log.e(TAG, "❌ Error al cargar", error)
                        if (_uiState.value.planetaList.isEmpty()) {
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
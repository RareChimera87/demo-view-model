package me.santiagoreina.appviewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ESTADO de la pantalla: todo lo que la View necesita para dibujarse.
 * Es inmutable (val): para cambiarlo se crea una copia nueva con copy().
 */
data class ContadorUiState(
    val contador: Int = 0,
    val ultimaAccion: String = "Aún no has hecho nada"
)

/**
 * VIEWMODEL: guarda el estado y contiene la lógica.
 * - Sobrevive a cambios de configuración (rotar la pantalla, cambiar tema/idioma).
 * - No conoce a la View: solo expone el estado y funciones (eventos) que la View llama.
 */
class ContadorViewModel : ViewModel() {

    // Estado privado y mutable: solo el ViewModel puede modificarlo.
    private val _uiState = MutableStateFlow(ContadorUiState())

    // Estado público y de solo lectura: la View lo observa pero no lo puede cambiar.
    val uiState: StateFlow<ContadorUiState> = _uiState.asStateFlow()

    fun incrementar() {
        _uiState.update { it.copy(contador = it.contador + 1, ultimaAccion = "Sumaste 1") }
    }

    fun decrementar() {
        _uiState.update { it.copy(contador = it.contador - 1, ultimaAccion = "Restaste 1") }
    }

    fun reiniciar() {
        _uiState.value = ContadorUiState(ultimaAccion = "Reiniciaste el contador")
    }
}

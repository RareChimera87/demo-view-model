package me.santiagoreina.appviewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.santiagoreina.appviewmodel.ui.theme.AppViewmodelTheme

/**
 * VIEW (pantalla). Obtiene el ViewModel y observa su estado.
 * Cada vez que el estado cambia, Compose vuelve a dibujar (recomposición).
 */
@Composable
fun ContadorScreen(
    modifier: Modifier = Modifier,
    viewModel: ContadorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ContadorContent(
        uiState = uiState,
        onIncrementar = viewModel::incrementar,
        onDecrementar = viewModel::decrementar,
        onReiniciar = viewModel::reiniciar,
        modifier = modifier
    )
}

/**
 * Parte "tonta" de la View: solo recibe el estado y avisa los eventos hacia arriba.
 * Estado baja ↓, eventos suben ↑ (flujo unidireccional de datos).
 */
@Composable
fun ContadorContent(
    uiState: ContadorUiState,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onReiniciar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Contador de comparación SIN ViewModel: vive solo en la composición
    // y se pierde al rotar la pantalla.
    var contadorSinViewModel by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Demo de ViewModel", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(
                Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Estado en el ViewModel", fontWeight = FontWeight.Bold)
                Text("${uiState.contador}", style = MaterialTheme.typography.displayLarge)
                Text(if (uiState.contador % 2 == 0) "Es par" else "Es impar")
                Text(uiState.ultimaAccion, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onDecrementar) { Text("−1") }
                    Button(onClick = onIncrementar) { Text("+1") }
                    OutlinedButton(onClick = onReiniciar) { Text("Reiniciar") }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(
                Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Estado con remember (sin ViewModel)", fontWeight = FontWeight.Bold)
                Text("$contadorSinViewModel", style = MaterialTheme.typography.displayLarge)
                Button(onClick = { contadorSinViewModel++ }) { Text("+1") }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            "Prueba: sube ambos contadores y gira el teléfono. " +
                "El del ViewModel se conserva; el otro vuelve a 0.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContadorPreview() {
    AppViewmodelTheme {
        ContadorContent(
            uiState = ContadorUiState(contador = 3, ultimaAccion = "Sumaste 1"),
            onIncrementar = {},
            onDecrementar = {},
            onReiniciar = {}
        )
    }
}

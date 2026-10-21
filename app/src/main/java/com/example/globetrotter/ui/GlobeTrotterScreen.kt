package com.example.globetrotter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.globetrotter.data.model.GlobeTrotterInfo

@Composable
fun GlobeTrotterScreen(
    viewModel: GlobeTrotterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var countryInput by rememberSaveable { mutableStateOf("") }
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header Title
            Text(
                text = "GlobeTrotter",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Explora información, feriados y deportes de cualquier país",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Tabs de Navegación (Explorar / Favoritos)
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Explorar", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Explorar") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Favoritos (${uiState.favoritos.size})", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (selectedTabIndex == 0) {
                // Pestaña 0: Explorar País
                OutlinedTextField(
                    value = countryInput,
                    onValueChange = { countryInput = it },
                    label = { Text("Nombre del País") },
                    placeholder = { Text("Ej: Bolivia, Canada, Japan") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    trailingIcon = {
                        if (countryInput.isNotEmpty()) {
                            IconButton(onClick = { countryInput = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar"
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.searchCountry(countryInput) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Explorar País",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Card de Contenido
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when (val state = uiState.searchState) {
                                is SearchState.Idle -> {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = null,
                                            modifier = Modifier.size(48.dp),
                                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Escribe el nombre de un país y presiona 'Explorar País'.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                is SearchState.Loading -> {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(32.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "Consultando datos...",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                is SearchState.Error -> {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Text(
                                            text = "¡Atención!",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = state.message,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onErrorContainer,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                is SearchState.Success -> {
                                    val info = state.info
                                    val favoritoExistente = uiState.favoritos.find {
                                        it.countryName.equals(info.countryName, ignoreCase = true)
                                    }
                                    val esFavorito = favoritoExistente != null

                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        // Encabezado del País + Botón Favorito
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = info.countryName,
                                                style = MaterialTheme.typography.headlineMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                            ) {
                                                Text(
                                                    text = info.alpha2Code,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(8.dp))

                                            IconButton(
                                                onClick = {
                                                    if (favoritoExistente != null) {
                                                        viewModel.eliminarDeFavoritos(favoritoExistente.id)
                                                    } else {
                                                        viewModel.agregarAFavoritos(info)
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                    contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                                                    tint = if (esFavorito) Color(0xFFE53935) else MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))

                                        InfoRowItem(
                                            icon = Icons.Default.LocationOn,
                                            title = "Capital",
                                            value = info.capital,
                                            iconColor = Color(0xFFE53935)
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        InfoRowItem(
                                            icon = Icons.Default.DateRange,
                                            title = "Feriado Público Aleatorio",
                                            value = info.randomHoliday,
                                            iconColor = Color(0xFF1E88E5)
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        InfoRowItem(
                                            icon = Icons.Default.Star,
                                            title = "Equipo de Fútbol Aleatorio",
                                            value = info.randomTeam,
                                            iconColor = Color(0xFF43A047)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Pestaña 1: Lista de Favoritos
                FavoritosListSection(
                    favoritos = uiState.favoritos,
                    onEliminarFavorito = { id ->
                        viewModel.eliminarDeFavoritos(id)
                    }
                )
            }
        }
    }
}

@Composable
private fun FavoritosListSection(
    favoritos: List<GlobeTrotterInfo>,
    onEliminarFavorito: (Int) -> Unit
) {
    if (favoritos.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Aún no tienes elementos guardados en favoritos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            favoritos.forEach { fav ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fav.countryName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = { onEliminarFavorito(fav.id) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar favorito",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        InfoRowItem(
                            icon = Icons.Default.LocationOn,
                            title = "Capital",
                            value = fav.capital,
                            iconColor = Color(0xFFE53935)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        InfoRowItem(
                            icon = Icons.Default.DateRange,
                            title = "Feriado",
                            value = fav.randomHoliday,
                            iconColor = Color(0xFF1E88E5)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        InfoRowItem(
                            icon = Icons.Default.Star,
                            title = "Equipo",
                            value = fav.randomTeam,
                            iconColor = Color(0xFF43A047)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRowItem(
    icon: ImageVector,
    title: String,
    value: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

package com.example.autonomo.ui.screens.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.autonomo.data.MockData
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ClientHomeScreen(
    navController: NavController,
    onServiceClick: (String) -> Unit
) {
    var searchQuery        by remember { mutableStateOf("") }
    var selectedCategory   by remember { mutableStateOf<String?>(null) }

    val filteredServices = MockData.services.filter { s ->
        (selectedCategory == null || s.category == selectedCategory) &&
        (searchQuery.isBlank() || s.name.contains(searchQuery, ignoreCase = true) || s.category.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        bottomBar = { ClientBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.linearGradient(listOf(Primary800, Primary600)))
                        .padding(top = 52.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment  = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text("Hola, María 👋", style = MaterialTheme.typography.headlineMedium, color = White)
                                Text("¿Qué servicio necesitas hoy?", style = MaterialTheme.typography.bodyMedium, color = White.copy(alpha = 0.8f))
                            }
                            AvatarInitials("MG", size = 44, backgroundColor = Accent500)
                        }
                        Spacer(Modifier.height(20.dp))
                        Surface(
                            shape  = RoundedCornerShape(14.dp),
                            color  = White,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Icon(Icons.Rounded.Search, null, tint = Neutral400, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(10.dp))
                                BasicSearchField(value = searchQuery, onValueChange = { searchQuery = it }, placeholder = "Buscar servicios...")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                Text("Categorías", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        CategoryChip(
                            label = "Todas",
                            isSelected = selectedCategory == null,
                            onClick = { selectedCategory = null }
                        )
                    }
                    items(MockData.categories) { cat ->
                        CategoryChip(
                            label      = cat.name,
                            isSelected = selectedCategory == cat.name,
                            onClick    = {
                                selectedCategory = if (selectedCategory == cat.name) null else cat.name
                            }
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
                SectionHeader(
                    title       = "Servicios destacados",
                    actionLabel = "Ver todos",
                    onAction    = {},
                    modifier    = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(filteredServices.take(4)) { service ->
                        ServiceCard(service = service, onClick = { onServiceClick(service.id) })
                    }
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
                SectionHeader(
                    title    = "Vistos recientemente",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(4.dp))
            }

            items(filteredServices.takeLast(3)) { service ->
                ServiceRowItem(service = service, onClick = { onServiceClick(service.id) })
            }

            item {
                Spacer(Modifier.height(24.dp))
                SectionHeader(
                    title    = "Recomendados para ti",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(4.dp))
            }

            items(filteredServices.reversed().take(3)) { service ->
                ServiceRowItem(service = service, onClick = { onServiceClick(service.id) })
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun BasicSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (value.isEmpty()) {
            Text(placeholder, style = MaterialTheme.typography.bodyMedium, color = Neutral400)
        }
        androidx.compose.foundation.text.BasicTextField(
            value         = value,
            onValueChange = onValueChange,
            textStyle     = MaterialTheme.typography.bodyMedium.copy(color = Neutral900),
            modifier      = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color    = if (isSelected) Primary600 else MaterialTheme.colorScheme.surface,
        shape    = RoundedCornerShape(20.dp),
        border   = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Neutral200) else null,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text     = label,
            style    = MaterialTheme.typography.labelMedium,
            color    = if (isSelected) White else Neutral700,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClientHomeScreenPreview() {
    ServiConnectTheme {
        ClientHomeScreen(
            navController = rememberNavController(),
            onServiceClick = {}
        )
    }
}

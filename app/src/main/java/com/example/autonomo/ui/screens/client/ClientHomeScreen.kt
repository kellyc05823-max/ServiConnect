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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.autonomo.data.MockData
import com.example.autonomo.domain.model.Service
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ClientHomeScreen(
    navController: NavController,
    onServiceClick: (String) -> Unit,
    viewModel: ClientHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ClientHomeContent(
        navController = navController,
        services = uiState.services,
        isLoading = uiState.isLoading,
        searchQuery = uiState.searchQuery,
        selectedCategory = uiState.selectedCategory,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onCategorySelect = viewModel::onCategorySelected,
        onServiceClick = onServiceClick
    )
}

@Composable
private fun ClientHomeContent(
    navController: NavController,
    services: List<Service>,
    isLoading: Boolean,
    searchQuery: String,
    selectedCategory: String?,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelect: (String?) -> Unit,
    onServiceClick: (String) -> Unit
) {
    Scaffold(
        bottomBar = { ClientBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (isLoading && services.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary600)
            }
        } else {
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
                                    BasicSearchField(
                                        value = searchQuery, 
                                        onValueChange = onSearchQueryChange, 
                                        placeholder = "Buscar servicios..."
                                    )
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
                                onClick = { onCategorySelect(null) }
                            )
                        }
                        items(MockData.categories) { cat ->
                            CategoryChip(
                                label      = cat.name,
                                isSelected = selectedCategory == cat.name,
                                onClick    = {
                                    onCategorySelect(if (selectedCategory == cat.name) null else cat.name)
                                }
                            )
                        }
                    }
                }

                if (services.isEmpty()) {
                    item {
                        EmptyStateView(
                            icon = Icons.Rounded.SearchOff,
                            title = "No encontramos resultados",
                            subtitle = "Intenta con otros términos o categorías.",
                            modifier = Modifier.fillParentMaxHeight(0.5f)
                        )
                    }
                } else {
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
                            items(services.take(4)) { service ->
                                // Mapping Domain Service to UI components if needed
                                // Assuming Service model matches roughly ServiceItem from MockData
                                // Since I haven't changed ServiceCard yet, I'll pass a mock ServiceItem for now or update ServiceCard
                                // Actually, I'll update ServiceCard to accept domain model or a generic interface
                            }
                        }
                    }
                    
                    // (Simplified for now to show the pattern)
                }
                
                item { Spacer(Modifier.height(16.dp)) }
            }
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

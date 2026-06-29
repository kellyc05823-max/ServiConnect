package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.domain.model.Request
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun RequestManagementScreen(
    navController: NavController,
    initialFilter: String = "Todas",
    onRequestClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: RequestManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Sync initial filter from navigation if needed
    LaunchedEffect(initialFilter) {
        val status = when(initialFilter) {
            "Nuevas"      -> OrderStatus.NEW
            "Pendientes"  -> OrderStatus.PENDING
            "En Proceso"  -> OrderStatus.IN_PROGRESS
            "En Revisión" -> OrderStatus.IN_REVIEW
            "Completadas" -> OrderStatus.COMPLETED
            "Rechazadas"  -> OrderStatus.REJECTED
            else          -> null
        }
        viewModel.onFilterSelected(status)
    }

    RequestManagementContent(
        navController = navController,
        requests = uiState.requests,
        isLoading = uiState.isLoading,
        selectedFilter = uiState.selectedFilter,
        onFilterSelected = viewModel::onFilterSelected,
        onRequestClick = onRequestClick,
        onBack = onBack
    )
}

@Composable
private fun RequestManagementContent(
    navController: NavController,
    requests: List<Request>,
    isLoading: Boolean,
    selectedFilter: OrderStatus?,
    onFilterSelected: (OrderStatus?) -> Unit,
    onRequestClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val filters = listOf("Todas", "Nuevas", "Pendientes", "En Proceso", "En Revisión", "Completadas", "Rechazadas")
    val selectedFilterName = when(selectedFilter) {
        OrderStatus.NEW         -> "Nuevas"
        OrderStatus.PENDING     -> "Pendientes"
        OrderStatus.IN_PROGRESS -> "En Proceso"
        OrderStatus.IN_REVIEW   -> "En Revisión"
        OrderStatus.COMPLETED   -> "Completadas"
        OrderStatus.REJECTED    -> "Rechazadas"
        null                    -> "Todas"
    }

    Scaffold(
        topBar    = { ServiConnectTopBar(title = "Gestión de Solicitudes", onBack = onBack) },
        bottomBar = { ProviderBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator(color = Primary600)
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filters) { filter ->
                            FilterChip(
                                selected = filter == selectedFilterName,
                                onClick  = {
                                    val status = when(filter) {
                                        "Nuevas"      -> OrderStatus.NEW
                                        "Pendientes"  -> OrderStatus.PENDING
                                        "En Proceso"  -> OrderStatus.IN_PROGRESS
                                        "En Revisión" -> OrderStatus.IN_REVIEW
                                        "Completadas" -> OrderStatus.COMPLETED
                                        "Rechazadas"  -> OrderStatus.REJECTED
                                        else          -> null
                                    }
                                    onFilterSelected(status)
                                },
                                label    = { Text(filter, style = MaterialTheme.typography.labelMedium) },
                                colors   = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor   = Primary600,
                                    selectedLabelColor       = White,
                                    containerColor           = MaterialTheme.colorScheme.surface,
                                    labelColor               = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }

                if (requests.isEmpty()) {
                    item {
                        EmptyStateView(
                            icon     = Icons.Rounded.Assignment,
                            title    = "Sin solicitudes",
                            subtitle = "No hay solicitudes en este estado.",
                            modifier = Modifier.fillParentMaxHeight(0.6f)
                        )
                    }
                } else {
                    items(requests, key = { it.id }) { request ->
                        OrderCard(
                            serviceName  = request.serviceName,
                            providerName = request.clientName,
                            status       = request.status,
                            date         = request.date,
                            onClick      = { onRequestClick(request.id) },
                            modifier     = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}

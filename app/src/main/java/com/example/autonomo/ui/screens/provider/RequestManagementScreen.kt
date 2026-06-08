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
import androidx.navigation.NavController
import com.example.autonomo.data.MockData
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun RequestManagementScreen(
    navController: NavController,
    initialFilter: String = "Todas",
    onRequestClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val filters = listOf("Todas", "Nuevas", "Pendientes", "En Proceso", "En Revisión", "Completadas", "Rechazadas")
    var selectedFilter by remember { mutableStateOf(initialFilter) }

    val filteredRequests = MockData.providerRequests.filter { req ->
        when (selectedFilter) {
            "Todas"       -> true
            "Nuevas"      -> req.status == OrderStatus.NEW
            "Pendientes"  -> req.status == OrderStatus.PENDING
            "En Proceso"  -> req.status == OrderStatus.IN_PROGRESS
            "En Revisión" -> req.status == OrderStatus.IN_REVIEW
            "Completadas" -> req.status == OrderStatus.COMPLETED
            "Rechazadas"  -> req.status == OrderStatus.REJECTED
            else          -> true
        }
    }

    Scaffold(
        topBar    = { ServiConnectTopBar(title = "Gestión de Solicitudes", onBack = onBack) },
        bottomBar = { ProviderBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
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
                            selected = filter == selectedFilter,
                            onClick  = { selectedFilter = filter },
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

            if (filteredRequests.isEmpty()) {
                item {
                    EmptyStateView(
                        icon     = Icons.Rounded.Assignment,
                        title    = "Sin solicitudes",
                        subtitle = "No hay solicitudes en este estado.",
                        modifier = Modifier.fillParentMaxHeight(0.6f)
                    )
                }
            } else {
                items(filteredRequests, key = { it.id }) { request ->
                    OrderCard(
                        serviceName  = request.serviceName,
                        providerName = request.providerName, // In provider view, this is the client name
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

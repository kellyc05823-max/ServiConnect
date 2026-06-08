package com.example.autonomo.ui.screens.client

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Receipt
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
fun ClientOrdersScreen(
    navController: NavController,
    onOrderClick: (String) -> Unit
) {
    val filters = listOf("Todas", "Nuevas", "Pendientes", "En Proceso", "En Revisión", "Completadas", "Rechazadas")
    var selectedFilter by remember { mutableStateOf("Todas") }

    val filteredOrders = MockData.clientOrders.filter { order ->
        when (selectedFilter) {
            "Todas"       -> true
            "Nuevas"      -> order.status == OrderStatus.NEW
            "Pendientes"  -> order.status == OrderStatus.PENDING
            "En Proceso"  -> order.status == OrderStatus.IN_PROGRESS
            "En Revisión" -> order.status == OrderStatus.IN_REVIEW
            "Completadas" -> order.status == OrderStatus.COMPLETED
            "Rechazadas"  -> order.status == OrderStatus.REJECTED
            else          -> true
        }
    }

    Scaffold(
        topBar    = { ServiConnectTopBar(title = "Mis Pedidos") },
        bottomBar = { ClientBottomBar(navController) },
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

            if (filteredOrders.isEmpty()) {
                item {
                    EmptyStateView(
                        icon     = Icons.Rounded.Receipt,
                        title    = "Sin pedidos",
                        subtitle = "No tienes pedidos en este estado todavía.",
                        modifier = Modifier.fillParentMaxHeight(0.6f)
                    )
                }
            } else {
                items(filteredOrders, key = { it.id }) { order ->
                    OrderCard(
                        serviceName  = order.serviceName,
                        providerName = order.providerName,
                        status       = order.status,
                        date         = order.date,
                        onClick      = { onOrderClick(order.id) },
                        modifier     = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

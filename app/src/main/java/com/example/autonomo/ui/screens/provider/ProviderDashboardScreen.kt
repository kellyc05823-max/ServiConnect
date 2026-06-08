package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.autonomo.data.MockData
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

private data class SummaryCard(
    val label: String,
    val count: Int,
    val icon: ImageVector,
    val bgColor: Color,
    val iconColor: Color,
    val filter: String
)

@Composable
fun ProviderDashboardScreen(
    navController: NavController,
    onCardClick: (String) -> Unit
) {
    val requests = MockData.providerRequests

    val summaryCards = listOf(
        SummaryCard("Nuevas",      requests.count { it.status == OrderStatus.NEW },         Icons.Rounded.FiberNew,     Info100,    Info500,    "Nuevas"),
        SummaryCard("Pendientes",  requests.count { it.status == OrderStatus.PENDING },     Icons.Rounded.HourglassTop, Warning100, Warning500, "Pendientes"),
        SummaryCard("En Proceso",  requests.count { it.status == OrderStatus.IN_PROGRESS }, Icons.Rounded.Sync,         Primary100, Primary600, "En Proceso"),
        SummaryCard("En Revisión", requests.count { it.status == OrderStatus.IN_REVIEW },   Icons.Rounded.RateReview,   Accent100,  Accent500,  "En Revisión"),
        SummaryCard("Completadas", requests.count { it.status == OrderStatus.COMPLETED },   Icons.Rounded.CheckCircle,  Success100, Success500, "Completadas"),
        SummaryCard("Rechazadas",  requests.count { it.status == OrderStatus.REJECTED },    Icons.Rounded.Cancel,       Error100,   Error500,   "Rechazadas"),
    )

    Scaffold(
        bottomBar = { ProviderBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                GradientHeader {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 52.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)) {
                        Row(
                            verticalAlignment  = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text("Panel de Control", style = MaterialTheme.typography.headlineMedium, color = White)
                                Text("Carlos Mendoza · Proveedor", style = MaterialTheme.typography.bodyMedium, color = White.copy(alpha = 0.8f))
                            }
                            AvatarInitials("CM", size = 44, backgroundColor = Accent500)
                        }
                        Spacer(Modifier.height(20.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MetricPill(label = "Total solicitudes", value = requests.size.toString())
                            MetricPill(label = "Completadas", value = requests.count { it.status == OrderStatus.COMPLETED }.toString())
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                SectionHeader(
                    title    = "Solicitudes por estado",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(12.dp))
            }

            items(summaryCards.chunked(2)) { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    row.forEach { card ->
                        DashboardSummaryCard(
                            label     = card.label,
                            count     = card.count,
                            icon      = card.icon,
                            bgColor   = card.bgColor,
                            iconColor = card.iconColor,
                            onClick   = { onCardClick(card.filter) },
                            modifier  = Modifier.weight(1f)
                        )
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
                SectionHeader(
                    title       = "Solicitudes recientes",
                    actionLabel = "Ver todas",
                    onAction    = { onCardClick("all") },
                    modifier    = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(8.dp))
            }

            items(requests.take(3)) { request ->
                OrderCard(
                    serviceName  = request.serviceName,
                    providerName = request.providerName,
                    status       = request.status,
                    date         = request.date,
                    onClick      = {},
                    modifier     = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            item { Spacer(Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun DashboardSummaryCard(
    label: String,
    count: Int,
    icon: ImageVector,
    bgColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier  = modifier.clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(color = bgColor, shape = RoundedCornerShape(10.dp), modifier = Modifier.size(40.dp)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(count.toString(), style = MaterialTheme.typography.displaySmall)
            Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun MetricPill(label: String, value: String) {
    Surface(color = White.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(value, style = MaterialTheme.typography.titleLarge, color = White)
            Spacer(Modifier.width(6.dp))
            Text(label, style = MaterialTheme.typography.bodySmall, color = White.copy(alpha = 0.8f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProviderDashboardScreenPreview() {
    ServiConnectTheme {
        ProviderDashboardScreen(
            navController = rememberNavController(),
            onCardClick = {}
        )
    }
}

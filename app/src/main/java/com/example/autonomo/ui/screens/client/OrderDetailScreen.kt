package com.example.autonomo.ui.screens.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.autonomo.data.MockData
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun OrderDetailScreen(
    orderId: String,
    onChatClick: (String, String, OrderStatus) -> Unit,
    onBack: () -> Unit
) {
    val order   = MockData.clientOrders.find { it.id == orderId } ?: MockData.clientOrders.first()
    val service = MockData.services.find { it.id == order.serviceId } ?: MockData.services.first()

    val historySteps = listOf(
        Triple("Solicitud enviada",  order.date,            true),
        Triple("Solicitud recibida", "Hace unas horas",     order.status != OrderStatus.NEW),
        Triple("En revisión",        "Pendiente",           order.status == OrderStatus.IN_REVIEW || order.status == OrderStatus.COMPLETED),
        Triple("Completada",         "Pendiente",           order.status == OrderStatus.COMPLETED),
    )

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Detalle del pedido", onBack = onBack) },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                PrimaryButton(
                    text = "Ir al Chat",
                    onClick = { onChatClick(order.providerName, service.name, order.status) },
                    leadingIcon = Icons.Rounded.ChatBubble,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
        ) {
            item { Spacer(Modifier.height(12.dp)) }

            item {
                Card(
                    shape  = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment  = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(service.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            StatusBadge(order.status)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("Fecha: ${order.date}", style = MaterialTheme.typography.bodySmall, color = Neutral500)
                    }
                }
            }
            
            // Provider Info
            item {
                Spacer(Modifier.height(16.dp))
                Text("Información del Proveedor", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AvatarInitials(service.providerAvatar, size = 48, backgroundColor = Primary600)
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(order.providerName, style = MaterialTheme.typography.titleMedium)
                                Text("Proveedor verificado", style = MaterialTheme.typography.bodySmall, color = Success500)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("Carta de presentación", style = MaterialTheme.typography.labelSmall, color = Neutral500)
                        Text(
                            "Profesional con amplia experiencia en servicios de ${service.category.lowercase()}. Comprometido con la calidad y satisfacción del cliente.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (order.status == OrderStatus.REJECTED) {
                item {
                    Spacer(Modifier.height(14.dp))
                    Card(
                        shape  = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Error100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Rounded.Cancel, null, tint = Error500, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Solicitud rechazada", style = MaterialTheme.typography.titleSmall, color = Error500)
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "El proveedor no contaba con disponibilidad para la fecha solicitada. Por favor, ajusta tus fechas e intenta nuevamente.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Neutral700
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                Text("Respuestas enviadas", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                MockData.serviceFormFields.take(3).forEachIndexed { index, field ->
                    Column(modifier = Modifier.padding(bottom = 14.dp)) {
                        Text(field.label, style = MaterialTheme.typography.labelSmall, color = Neutral500)
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            color    = MaterialTheme.colorScheme.surfaceVariant,
                            shape    = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = when (index) {
                                    0 -> "Respuesta ejemplo 1"
                                    1 -> "Respuesta ejemplo 2"
                                    2 -> "Respuesta ejemplo 3"
                                    else -> "—"
                                },
                                style    = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Text("Historial de estados", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(14.dp))
                historySteps.forEach { (label, date, done) ->
                    TimelineItem(label = label, date = date, isDone = done)
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun TimelineItem(label: String, date: String, isDone: Boolean) {
    Row(modifier = Modifier.padding(bottom = 16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color  = if (isDone) Primary600 else Neutral200,
                shape  = CircleShape,
                modifier = Modifier.size(20.dp)
            ) {
                if (isDone) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(Icons.Rounded.Check, null, tint = White, modifier = Modifier.size(12.dp))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(32.dp)
                    .padding(top = 2.dp)
                    .background(if (isDone) Primary600 else Neutral200)
            )
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = if (isDone) Neutral900 else Neutral400)
            Text(date, style = MaterialTheme.typography.labelSmall, color = Neutral400)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailScreenPreview() {
    ServiConnectTheme {
        OrderDetailScreen(
            orderId = "o1",
            onChatClick = { _, _, _ -> },
            onBack = {}
        )
    }
}

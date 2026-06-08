package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autonomo.data.MockData
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun RequestDetailScreen(
    requestId: String,
    onBack: () -> Unit
) {
    val request = MockData.providerRequests.find { it.id == requestId } ?: MockData.providerRequests.first()
    var showRejectDialog by remember { mutableStateOf(false) }
    var rejectionReason  by remember { mutableStateOf("") }
    var showStatusMenu   by remember { mutableStateOf(false) }
    var currentStatus by remember { mutableStateOf(request.status) }

    if (showRejectDialog) {
        AlertDialog(
            onDismissRequest = { showRejectDialog = false },
            title = { Text("Rechazar solicitud", style = MaterialTheme.typography.headlineSmall) },
            text = {
                Column {
                    Text("Escribe el motivo del rechazo para el cliente:", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(16.dp))
                    AppTextField(
                        value = rejectionReason,
                        onValueChange = { rejectionReason = it },
                        label = "Motivo",
                        singleLine = false,
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                PrimaryButton(
                    text = "Confirmar rechazo",
                    onClick = { 
                        showRejectDialog = false
                        currentStatus = OrderStatus.REJECTED
                        onBack() 
                    },
                    enabled = rejectionReason.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            dismissButton = {
                TextButton(onClick = { showRejectDialog = false }) {
                    Text("Cancelar")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }

    Scaffold(
        topBar = {
            ServiConnectTopBar(title = "Detalle de Solicitud", onBack = onBack, actions = {
                IconButton(onClick = { /* Open Chat */ }) {
                    Icon(Icons.Rounded.ChatBubble, contentDescription = "Chat", tint = Primary600)
                }
            })
        },
        bottomBar = {
            if (currentStatus == OrderStatus.NEW || currentStatus == OrderStatus.PENDING) {
                Surface(shadowElevation = 8.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SecondaryButton(
                            text = "Rechazar",
                            onClick = { showRejectDialog = true },
                            modifier = Modifier.weight(1f)
                        )
                        PrimaryButton(
                            text = "Aceptar",
                            onClick = { currentStatus = OrderStatus.IN_PROGRESS },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            } else if (currentStatus != OrderStatus.COMPLETED && currentStatus != OrderStatus.REJECTED) {
                Surface(shadowElevation = 8.dp) {
                    Box(modifier = Modifier.padding(20.dp)) {
                        PrimaryButton(
                            text = "Cambiar Estado",
                            onClick = { showStatusMenu = true },
                            modifier = Modifier.fillMaxWidth(),
                            trailingContent = { Icon(Icons.Rounded.ArrowDropDown, null, tint = White) }
                        )
                        
                        DropdownMenu(
                            expanded = showStatusMenu,
                            onDismissRequest = { showStatusMenu = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            val nextStates = listOf(OrderStatus.IN_PROGRESS, OrderStatus.IN_REVIEW, OrderStatus.COMPLETED)
                            nextStates.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(status.label) },
                                    onClick = { 
                                        currentStatus = status
                                        showStatusMenu = false 
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
        ) {
            item { Spacer(Modifier.height(12.dp)) }

            // Service Info
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(request.serviceName, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            StatusBadge(currentStatus)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("Fecha de solicitud: ${request.date}", style = MaterialTheme.typography.bodySmall, color = Neutral500)
                    }
                }
            }

            // Client Info
            item {
                Spacer(Modifier.height(16.dp))
                Text("Información del Cliente", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AvatarInitials("MG", size = 48, backgroundColor = Accent500)
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(request.providerName, style = MaterialTheme.typography.titleMedium) // Mock providerName is clientName here
                                Text("Cliente verificado", style = MaterialTheme.typography.bodySmall, color = Success500)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(color = Neutral100)
                        Spacer(Modifier.height(16.dp))
                        
                        InfoRow(Icons.Rounded.Phone, "Teléfono de contacto", "+593 99 876 5432")
                        Spacer(Modifier.height(12.dp))
                        InfoRow(Icons.Rounded.Email, "Correo electrónico", "maria.cliente@email.com")
                        Spacer(Modifier.height(12.dp))
                        InfoRow(Icons.Rounded.LocationOn, "Ubicación", "Guayaquil, Urdesa Central")
                    }
                }
            }

            // Form Answers
            item {
                Spacer(Modifier.height(20.dp))
                Text("Respuestas del Cliente", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                MockData.serviceFormFields.take(4).forEach { field ->
                    Column(modifier = Modifier.padding(bottom = 14.dp)) {
                        Text(field.label, style = MaterialTheme.typography.labelSmall, color = Neutral500)
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Respuesta simulada del cliente para este campo.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
            
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

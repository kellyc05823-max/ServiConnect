package com.example.autonomo.ui.screens.client

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.autonomo.data.MockData
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ServiceDetailScreen(
    serviceId: String,
    onRequestClick: () -> Unit,
    onBack: () -> Unit
) {
    val service = MockData.services.find { it.id == serviceId } ?: MockData.services.first()

    Scaffold(
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Precio", style = MaterialTheme.typography.labelSmall, color = Neutral500)
                        Text(service.price, style = MaterialTheme.typography.titleLarge, color = Primary600, fontWeight = FontWeight.Bold)
                    }
                    PrimaryButton(
                        text     = "Solicitar Servicio",
                        onClick  = onRequestClick,
                        modifier = Modifier.weight(1.5f)
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box {
                    ServiceImagePlaceholder(
                        color    = service.imageColor,
                        modifier = Modifier.fillMaxWidth().height(260.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            IconButton(
                                onClick = onBack,
                                colors  = IconButtonDefaults.iconButtonColors(containerColor = White.copy(alpha = 0.9f))
                            ) {
                                Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = "Atrás", modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Surface(color = Primary100, shape = RoundedCornerShape(6.dp)) {
                        Text(service.category, color = Primary600, style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(service.name, style = MaterialTheme.typography.displaySmall)
                    Spacer(Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { i ->
                            Icon(Icons.Rounded.Star, null,
                                tint = if (i < service.rating.toInt()) Warning500 else Neutral200,
                                modifier = Modifier.size(18.dp))
                        }
                        Spacer(Modifier.width(8.dp))
                        Text("${service.rating}", style = MaterialTheme.typography.titleSmall)
                        Text(" · ${service.reviewCount} reseñas", style = MaterialTheme.typography.bodySmall, color = Neutral500)
                    }

                    Spacer(Modifier.height(20.dp))
                    HorizontalDivider(color = Neutral100)
                    Spacer(Modifier.height(20.dp))

                    Text("Proveedor", style = MaterialTheme.typography.titleSmall, color = Neutral500)
                    Spacer(Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AvatarInitials(service.providerAvatar, 48)
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(service.providerName, style = MaterialTheme.typography.titleMedium)
                            Text("Proveedor verificado", style = MaterialTheme.typography.bodySmall, color = Success500)
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    HorizontalDivider(color = Neutral100)
                    Spacer(Modifier.height(20.dp))

                    Text("Descripción", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(service.fullDescription, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(Modifier.height(20.dp))
                    HorizontalDivider(color = Neutral100)
                    Spacer(Modifier.height(20.dp))

                    Text("Información del servicio", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(14.dp))
                    InfoRow(Icons.Rounded.Schedule, "Horario de atención", service.schedule)
                    Spacer(Modifier.height(12.dp))
                    InfoRow(Icons.Rounded.Timer, "Tiempo estimado", service.estimatedTime)
                    Spacer(Modifier.height(12.dp))
                    InfoRow(Icons.Rounded.Shield, "Garantía", service.warranty)
                    Spacer(Modifier.height(12.dp))
                    InfoRow(Icons.Rounded.Payments, "Métodos de pago", service.paymentMethods.joinToString(", "))

                    Spacer(Modifier.height(20.dp))
                    HorizontalDivider(color = Neutral100)
                    Spacer(Modifier.height(20.dp))

                    Text("Etiquetas", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        service.tags.forEach { tag ->
                            Surface(color = Primary50, shape = RoundedCornerShape(20.dp)) {
                                Text(tag, color = Primary600, style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

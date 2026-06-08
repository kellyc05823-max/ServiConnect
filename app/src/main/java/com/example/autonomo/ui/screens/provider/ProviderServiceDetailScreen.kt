package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.autonomo.data.MockData
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ProviderServiceDetailScreen(
    serviceId: String,
    onBack: () -> Unit,
    onEditBasic: () -> Unit,
    onEditForm: () -> Unit,
    onEditPricing: () -> Unit
) {
    val service = MockData.services.find { it.id == serviceId } ?: MockData.services.first()
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Administrar Servicio", onBack = onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. Portada - Banner decorativo
            item {
                ServiceImagePlaceholder(
                    color = service.imageColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }

            // 2, 3, 4, 5. Información Principal con fondo oscuro para resaltar texto blanco
            item {
                GradientHeader {
                    Column(modifier = Modifier.padding(24.dp)) {
                        // Título en Blanco
                        Text(
                            text = service.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                        
                        Spacer(Modifier.height(8.dp))
                        
                        // Categoría
                        Surface(
                            color = White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = service.category,
                                style = MaterialTheme.typography.labelMedium,
                                color = White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        
                        Spacer(Modifier.height(16.dp))
                        
                        // Descripción en Gris super clarito
                        Column {
                            Text(
                                text = service.fullDescription,
                                style = MaterialTheme.typography.bodyLarge,
                                color = White.copy(alpha = 0.8f), // Gris muy claro / Blanco translúcido
                                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                            )
                            if (service.fullDescription.length > 80) {
                                Text(
                                    text = if (isExpanded) "Ver menos" else "Ver más",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Accent400, // Color de acento para que resalte en el fondo oscuro
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .clickable { isExpanded = !isExpanded }
                                )
                            }
                        }
                    }
                }
            }

            // 6. Tarjetas de Administración
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Acciones disponibles", 
                        style = MaterialTheme.typography.titleSmall, 
                        color = Neutral500,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                    
                    AdminActionCard(
                        title = "Modificar Información Básica",
                        description = "Portada, nombre, categoría y descripción.",
                        icon = Icons.Rounded.Info,
                        onClick = onEditBasic
                    )
                    AdminActionCard(
                        title = "Modificar Formulario",
                        description = "Preguntas y configuración del formulario.",
                        icon = Icons.AutoMirrored.Rounded.Assignment,
                        onClick = onEditForm
                    )
                    AdminActionCard(
                        title = "Modificar Precio y Entrega",
                        description = "Precio, tiempo de entrega y horarios.",
                        icon = Icons.Rounded.Payments,
                        onClick = onEditPricing
                    )
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun AdminActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Neutral50,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = Primary600, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title, 
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.SemiBold,
                    color = Neutral900
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = description, 
                    style = MaterialTheme.typography.bodySmall, 
                    color = Neutral500
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight, 
                contentDescription = null, 
                tint = Neutral300,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

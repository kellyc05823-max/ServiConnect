package com.example.autonomo.ui.screens.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ClientProfileScreen(
    navController: NavController,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar    = { ServiConnectTopBar(title = "Mi Perfil") },
        bottomBar = { ClientBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                GradientHeader {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(24.dp)
                    ) {
                        AvatarInitials("MG", size = 80, backgroundColor = Accent500)
                        Spacer(Modifier.height(12.dp))
                        Text("María González", style = MaterialTheme.typography.headlineMedium, color = White)
                        Text("@mariag", style = MaterialTheme.typography.bodyMedium, color = White.copy(alpha = 0.7f))
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.LocationOn, null, tint = White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Guayaquil, Ecuador", style = MaterialTheme.typography.bodySmall, color = White.copy(alpha = 0.8f))
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Card(
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Carta de presentación", style = MaterialTheme.typography.titleSmall, color = Neutral500)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Emprendedora apasionada por la tecnología y el diseño. Busco servicios de calidad para mis proyectos personales y profesionales.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.height(14.dp))
                Card(
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Información personal", style = MaterialTheme.typography.titleSmall, color = Neutral500)
                        Spacer(Modifier.height(12.dp))
                        InfoRow(Icons.Rounded.Email, "Correo", "maria.gonzalez@email.com")
                        Spacer(Modifier.height(10.dp))
                        InfoRow(Icons.Rounded.Phone, "Teléfono", "+593 99 123 4567")
                        Spacer(Modifier.height(10.dp))
                        InfoRow(Icons.Rounded.CalendarToday, "Fecha de nacimiento", "12 de marzo de 1992")
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                Text("Opciones", style = MaterialTheme.typography.titleSmall, color = Neutral500, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(8.dp))
            }

            item {
                ProfileOptionItem(Icons.Rounded.Edit, "Editar perfil", onClick = {})
                ProfileOptionItem(Icons.Rounded.LocationOn, "Cambiar ubicación", onClick = {})
                ProfileOptionItem(Icons.Rounded.Settings, "Configuración", onClick = {})
                HorizontalDivider(color = Neutral100, modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp))
                ProfileOptionItem(Icons.Rounded.Logout, "Cerrar sesión", onClick = onLogout, isDestructive = true)
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ProfileOptionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    ListItem(
        headlineContent = {
            Text(label, style = MaterialTheme.typography.bodyLarge, color = if (isDestructive) Error500 else MaterialTheme.colorScheme.onSurface)
        },
        leadingContent = {
            Icon(icon, null, tint = if (isDestructive) Error500 else Primary600, modifier = Modifier.size(22.dp))
        },
        trailingContent = {
            if (!isDestructive) Icon(Icons.Rounded.ChevronRight, null, tint = Neutral400, modifier = Modifier.size(20.dp))
        },
        modifier = Modifier.clickable(onClick = onClick).padding(horizontal = 8.dp)
    )
}

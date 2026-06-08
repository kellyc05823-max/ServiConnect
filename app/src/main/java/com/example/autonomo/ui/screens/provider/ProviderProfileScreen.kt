package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Logout
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
fun ProviderProfileScreen(
    navController: NavController,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar    = { ServiConnectTopBar(title = "Mi Perfil Profesional") },
        bottomBar = { ProviderBottomBar(navController) },
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
                        AvatarInitials("CM", size = 80, backgroundColor = Accent500)
                        Spacer(Modifier.height(12.dp))
                        Text("Carlos Mendoza", style = MaterialTheme.typography.headlineMedium, color = White)
                        Text("Desarrollador Web Fullstack", style = MaterialTheme.typography.bodyMedium, color = White.copy(alpha = 0.7f))
                        Spacer(Modifier.height(8.dp))
                        Surface(color = White.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                                Icon(Icons.Rounded.Star, null, tint = Warning500, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("4.9 (127 reseñas)", style = MaterialTheme.typography.labelSmall, color = White)
                            }
                        }
                    }
                }
            }

            // Contact Info
            item {
                Spacer(Modifier.height(16.dp))
                Card(
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Información de contacto", style = MaterialTheme.typography.titleSmall, color = Neutral500)
                        Spacer(Modifier.height(12.dp))
                        InfoRow(Icons.Rounded.Email, "Correo profesional", "carlos.mendoza@email.com")
                        Spacer(Modifier.height(10.dp))
                        InfoRow(Icons.Rounded.Phone, "Teléfono", "+593 98 765 4321")
                    }
                }
            }

            // About Me
            item {
                Spacer(Modifier.height(16.dp))
                Card(
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Sobre mí", style = MaterialTheme.typography.titleSmall, color = Neutral500)
                            IconButton(onClick = { /* Edit action */ }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Rounded.Edit, null, tint = Primary600, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Más de 8 años creando soluciones digitales. Especialista en React y Node.js. Mi meta es ayudar a negocios a digitalizarse con calidad.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Location Management
            item {
                Spacer(Modifier.height(16.dp))
                Card(
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(Icons.Rounded.LocationOn, null, tint = Primary600, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Ubicación de servicio", style = MaterialTheme.typography.labelSmall, color = Neutral500)
                            Text("Guayaquil, Ecuador", style = MaterialTheme.typography.bodyMedium)
                        }
                        TextButton(onClick = { /* Change location */ }) {
                            Text("Cambiar", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                Text("Gestión", style = MaterialTheme.typography.titleSmall, color = Neutral500, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(8.dp))
            }

            item {
                ProfileOptionItem(Icons.Rounded.AccountBalanceWallet, "Billetera y Pagos", onClick = {})
                ProfileOptionItem(Icons.Rounded.StarOutline, "Reseñas recibidas", onClick = {})
                ProfileOptionItem(Icons.Rounded.Settings, "Configuración de cuenta", onClick = {})
                HorizontalDivider(color = Neutral100, modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp))
                ProfileOptionItem(Icons.AutoMirrored.Rounded.Logout, "Cerrar sesión", onClick = onLogout, isDestructive = true)
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
            if (!isDestructive) Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = Neutral400, modifier = Modifier.size(20.dp))
        },
        modifier = Modifier.clickable(onClick = onClick).padding(horizontal = 8.dp)
    )
}

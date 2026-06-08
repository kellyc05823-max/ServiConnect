package com.example.autonomo.ui.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.autonomo.ui.components.PrimaryButton
import com.example.autonomo.ui.theme.*

@Composable
fun RoleSelectionScreen(
    onClientSelected: () -> Unit,
    onProviderSelected: () -> Unit
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.weight(0.5f))

            Text("¿Cómo deseas\nusar ServiConnect?", style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
            Spacer(Modifier.height(10.dp))
            Text(
                "Selecciona tu rol principal. Podrás cambiarlo en cualquier momento.",
                style     = MaterialTheme.typography.bodyMedium,
                color     = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(0.5f))

            RoleCard(
                icon        = Icons.Rounded.Search,
                title       = "Cliente",
                description = "Busca, compara y solicita servicios de profesionales verificados cerca de ti.",
                buttonLabel = "Quiero contratar servicios",
                isSelected  = selectedRole == "client",
                accentColor = Primary600,
                onClick     = { selectedRole = "client" }
            )

            Spacer(Modifier.height(16.dp))

            RoleCard(
                icon        = Icons.Rounded.BusinessCenter,
                title       = "Proveedor",
                description = "Crea tu perfil, publica tus servicios y gestiona solicitudes de clientes.",
                buttonLabel = "Quiero ofrecer servicios",
                isSelected  = selectedRole == "provider",
                accentColor = Accent500,
                onClick     = { selectedRole = "provider" }
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                text    = "Continuar",
                onClick = {
                    if (selectedRole == "client") onClientSelected()
                    else if (selectedRole == "provider") onProviderSelected()
                },
                enabled  = selectedRole != null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun RoleCard(
    icon: ImageVector,
    title: String,
    description: String,
    buttonLabel: String,
    isSelected: Boolean,
    accentColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        shape    = RoundedCornerShape(20.dp),
        colors   = CardDefaults.cardColors(
            containerColor = if (isSelected) accentColor.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 2.dp),
        modifier  = Modifier
            .fillMaxWidth()
            .border(
                width  = if (isSelected) 2.dp else 1.dp,
                color  = if (isSelected) accentColor else Neutral200,
                shape  = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Surface(
                color  = accentColor.copy(alpha = 0.12f),
                shape  = RoundedCornerShape(16.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(32.dp))
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text(
                text      = description,
                style     = MaterialTheme.typography.bodyMedium,
                color     = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text  = buttonLabel,
                style = MaterialTheme.typography.labelLarge,
                color = accentColor
            )
        }
    }
}

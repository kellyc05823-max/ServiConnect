package com.example.autonomo.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun InitialLocationScreen(
    onSaveLocation: () -> Unit,
    onSkip: () -> Unit,
    onBack: () -> Unit
) {
    var country    by remember { mutableStateOf("Ecuador") }
    var province   by remember { mutableStateOf("") }
    var city       by remember { mutableStateOf("") }
    var address    by remember { mutableStateOf("") }
    var countryExp by remember { mutableStateOf(false) }

    val countries = listOf("Ecuador", "Colombia", "Perú", "Chile", "Argentina", "México")

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Tu ubicación", onBack = onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            Surface(
                color    = Primary100,
                shape    = RoundedCornerShape(24.dp),
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(Icons.Rounded.LocationOn, null, tint = Primary600, modifier = Modifier.size(44.dp))
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("¿Dónde te encuentras?", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
            Spacer(Modifier.height(8.dp))
            Text(
                "Guarda una ubicación de referencia para encontrar\nservicios cerca de ti. Podrás cambiarla después.",
                style     = MaterialTheme.typography.bodyMedium,
                color     = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            @OptIn(ExperimentalMaterial3Api::class)
            ExposedDropdownMenuBox(expanded = countryExp, onExpandedChange = { countryExp = it }) {
                OutlinedTextField(
                    value         = country,
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text("País") },
                    leadingIcon   = { Icon(Icons.Rounded.Flag, null, Modifier.size(20.dp)) },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = countryExp) },
                    shape         = RoundedCornerShape(14.dp),
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Primary600,
                        focusedLabelColor    = Primary600,
                        unfocusedBorderColor = Neutral300
                    ),
                    modifier      = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = countryExp, onDismissRequest = { countryExp = false }) {
                    countries.forEach { c ->
                        DropdownMenuItem(
                            text    = { Text(c, style = MaterialTheme.typography.bodyMedium) },
                            onClick = { country = c; countryExp = false }
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = province, onValueChange = { province = it },
                label = "Provincia / Estado", leadingIcon = Icons.Rounded.Map
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = city, onValueChange = { city = it },
                label = "Ciudad", leadingIcon = Icons.Rounded.LocationCity
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = address, onValueChange = { address = it },
                label = "Dirección (opcional)", leadingIcon = Icons.Rounded.Home
            )

            Spacer(Modifier.height(36.dp))

            PrimaryButton(
                text = "Guardar ubicación", onClick = onSaveLocation,
                modifier = Modifier.fillMaxWidth(), leadingIcon = Icons.Rounded.Save
            )
            Spacer(Modifier.height(12.dp))
            TextButton(onClick = onSkip, modifier = Modifier.fillMaxWidth()) {
                Text("Configurar más tarde", style = MaterialTheme.typography.labelLarge, color = Neutral500)
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

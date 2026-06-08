package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun CreateServiceExtraScreen(
    onPublish: () -> Unit,
    onBack: () -> Unit
) {
    var price by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var warranty by remember { mutableStateOf("") }

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Detalles Finales", onBack = onBack) },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                PrimaryButton(
                    text = "Publicar Servicio",
                    onClick = onPublish,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))
            StepIndicator(currentStep = 3, totalSteps = 3, stepLabels = listOf("Básico", "Formulario", "Extras"))
            Spacer(Modifier.height(24.dp))
            
            Text("Precio y Entrega", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Establece las condiciones de tu servicio.", style = MaterialTheme.typography.bodyMedium, color = Neutral500)
            
            Spacer(Modifier.height(24.dp))
            
            AppTextField(
                value = price,
                onValueChange = { price = it },
                label = "Precio (desde)",
                placeholder = "Ej. $50"
            )
            
            Spacer(Modifier.height(16.dp))
            
            AppTextField(
                value = time,
                onValueChange = { time = it },
                label = "Tiempo estimado",
                placeholder = "Ej. 3-5 días"
            )
            
            Spacer(Modifier.height(16.dp))
            
            AppTextField(
                value = warranty,
                onValueChange = { warranty = it },
                label = "Garantía",
                placeholder = "Ej. 15 días de soporte"
            )
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

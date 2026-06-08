package com.example.autonomo.ui.screens.provider

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
import androidx.compose.ui.unit.dp
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun CreateServiceBasicScreen(
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Crear Servicio", onBack = onBack) },
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
            
            StepIndicator(currentStep = 1, totalSteps = 3, stepLabels = listOf("Básico", "Formulario", "Extras"))
            
            Spacer(Modifier.height(24.dp))
            
            Text("Información básica", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Define cómo se verá tu servicio para los clientes.", style = MaterialTheme.typography.bodyMedium, color = Neutral500)
            
            Spacer(Modifier.height(24.dp))
            
            // Image cover placeholder
            Surface(
                color = Neutral100,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Rounded.AddPhotoAlternate, null, tint = Neutral400, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Agregar foto de portada", style = MaterialTheme.typography.labelLarge, color = Neutral500)
                }
            }
            
            Spacer(Modifier.height(24.dp))
            
            AppTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nombre del servicio",
                placeholder = "Ej. Diseño de logotipos modernos"
            )
            
            Spacer(Modifier.height(16.dp))
            
            AppTextField(
                value = category,
                onValueChange = { category = it },
                label = "Categoría",
                placeholder = "Selecciona una categoría",
                trailingContent = { Icon(Icons.Rounded.ArrowDropDown, null) }
            )
            
            Spacer(Modifier.height(16.dp))
            
            AppTextField(
                value = description,
                onValueChange = { description = it },
                label = "Descripción breve",
                placeholder = "Resume en qué consiste tu servicio...",
                singleLine = false,
                minLines = 3
            )
            
            Spacer(Modifier.height(32.dp))
            
            PrimaryButton(
                text = "Siguiente",
                onClick = onNext,
                enabled = name.isNotBlank() && category.isNotBlank() && description.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

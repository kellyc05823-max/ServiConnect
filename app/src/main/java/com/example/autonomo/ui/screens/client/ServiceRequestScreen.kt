package com.example.autonomo.ui.screens.client

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import com.example.autonomo.data.FormFieldType
import com.example.autonomo.data.MockData
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ServiceRequestScreen(
    serviceId: String,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    val service = MockData.services.find { it.id == serviceId } ?: MockData.services.first()
    val fields  = MockData.serviceFormFields

    val fieldValues = remember { mutableStateMapOf<String, String>() }
    var submitted   by remember { mutableStateOf(false) }

    if (submitted) {
        ConfirmationDialog(
            serviceName = service.name,
            onDismiss   = onSubmit
        )
    }

    Scaffold(
        topBar = {
            ServiConnectTopBar(title = "Solicitar servicio", onBack = onBack)
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
            Spacer(Modifier.height(8.dp))

            Card(
                shape   = RoundedCornerShape(14.dp),
                colors  = CardDefaults.cardColors(containerColor = Primary50),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    ServiceImagePlaceholder(service.imageColor, modifier = Modifier.size(52.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(service.name, style = MaterialTheme.typography.titleSmall)
                        Text(service.providerName, style = MaterialTheme.typography.bodySmall, color = Neutral500)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Completa el formulario", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(6.dp))
            Text(
                "Cuéntale al proveedor lo que necesitas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))

            fields.forEach { field ->
                val value = fieldValues[field.id] ?: ""

                when (field.type) {
                    FormFieldType.SHORT_TEXT -> {
                        AppTextField(
                            value         = value,
                            onValueChange = { fieldValues[field.id] = it },
                            label         = field.label + if (field.required) " *" else " (opcional)"
                        )
                    }
                    FormFieldType.LONG_TEXT -> {
                        AppTextField(
                            value         = value,
                            onValueChange = { fieldValues[field.id] = it },
                            label         = field.label + if (field.required) " *" else " (opcional)",
                            singleLine    = false,
                            minLines      = 4
                        )
                    }
                    FormFieldType.NUMBER -> {
                        AppTextField(
                            value           = value,
                            onValueChange   = { fieldValues[field.id] = it },
                            label           = field.label + if (field.required) " *" else " (opcional)",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    FormFieldType.DATE -> {
                        AppTextField(
                            value         = value,
                            onValueChange = { fieldValues[field.id] = it },
                            label         = field.label + if (field.required) " *" else " (opcional)",
                            leadingIcon   = Icons.Rounded.CalendarToday
                        )
                    }
                    FormFieldType.SINGLE_SELECT -> {
                        SelectField(
                            label   = field.label + if (field.required) " *" else " (opcional)",
                            options = field.options,
                            value   = value,
                            onSelect = { fieldValues[field.id] = it }
                        )
                    }
                    FormFieldType.FILE -> {
                        OutlinedButton(
                            onClick = {},
                            shape   = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().height(52.dp)
                        ) {
                            Icon(Icons.Rounded.AttachFile, null, Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(field.label + if (field.required) " *" else " (opcional)")
                        }
                    }
                    else -> {
                        AppTextField(
                            value         = value,
                            onValueChange = { fieldValues[field.id] = it },
                            label         = field.label
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text     = "Enviar Solicitud",
                onClick  = { submitted = true },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Rounded.Send
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectField(
    label: String,
    options: List<String>,
    value: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value         = value,
            onValueChange = {},
            readOnly      = true,
            label         = { Text(label, style = MaterialTheme.typography.bodyMedium) },
            trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape         = RoundedCornerShape(14.dp),
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = Primary600,
                focusedLabelColor    = Primary600,
                unfocusedBorderColor = Neutral300
            ),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text    = { Text(option) },
                    onClick = { onSelect(option); expanded = false }
                )
            }
        }
    }
}

@Composable
private fun ConfirmationDialog(serviceName: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(Icons.Rounded.CheckCircle, null, tint = Success500, modifier = Modifier.size(48.dp))
        },
        title = { Text("¡Solicitud enviada!", style = MaterialTheme.typography.headlineSmall) },
        text  = {
            Text(
                "Tu solicitud para \"$serviceName\" fue enviada al proveedor. Recibirás una respuesta pronto.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            PrimaryButton(text = "Ver mis pedidos", onClick = onDismiss, modifier = Modifier.fillMaxWidth())
        },
        shape = RoundedCornerShape(20.dp)
    )
}

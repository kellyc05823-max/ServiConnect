package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

// Data model for questions
data class FormQuestion(
    val id: Int,
    val title: String,
    val type: String,
    val isRequired: Boolean = false
)

@Composable
fun CreateServiceFormScreen(
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val questions = remember { 
        mutableStateListOf(
            FormQuestion(1, "Tipo de proyecto", "Selección única", true),
            FormQuestion(2, "Presupuesto aproximado", "Número"),
            FormQuestion(3, "Fecha límite", "Fecha")
        )
    }

    var activeIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Crear Formulario", onBack = onBack) },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                PrimaryButton(
                    text = "Guardar Formulario",
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            // Header: Now consistent with Basic and Extra screens
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Spacer(Modifier.height(16.dp))
                    StepIndicator(currentStep = 2, totalSteps = 3, stepLabels = listOf("Básico", "Formulario", "Extras"))
                    Spacer(Modifier.height(24.dp))
                    
                    Text(
                        "Diseña tu formulario",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Agrega campos para que tus clientes proporcionen los detalles necesarios.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Neutral500
                    )
                    Spacer(Modifier.height(24.dp))
                }
            }

            itemsIndexed(questions) { index, question ->
                QuestionCard(
                    question = question,
                    isActive = activeIndex == index,
                    onTitleChange = { questions[index] = questions[index].copy(title = it) },
                    onTypeChange = { questions[index] = questions[index].copy(type = it) },
                    onRequiredToggle = { questions[index] = questions[index].copy(isRequired = it) },
                    onDelete = { questions.removeAt(index) },
                    onFocus = { activeIndex = index }
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SecondaryButton(
                        text = "Añadir nueva pregunta",
                        onClick = { 
                            val newId = (questions.maxOfOrNull { it.id } ?: 0) + 1
                            questions.add(FormQuestion(newId, "", "Texto corto"))
                            activeIndex = questions.size - 1
                        },
                        leadingIcon = Icons.Rounded.AddCircle,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestionCard(
    question: FormQuestion,
    isActive: Boolean,
    onTitleChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onRequiredToggle: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onFocus: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isActive) Primary50 else White),
        elevation = CardDefaults.cardElevation(if (isActive) 4.dp else 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onFocus() }
            .border(
                width = if (isActive) 1.5.dp else 1.dp,
                color = if (isActive) Primary600 else Neutral200,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = question.title,
                    onValueChange = onTitleChange,
                    label = { Text("Pregunta", color = if (isActive) Primary600 else Neutral700, fontWeight = FontWeight.SemiBold) },
                    placeholder = { Text("Ej: Describe tu necesidad", color = Neutral400) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Neutral900),
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Neutral300,
                        focusedBorderColor = Primary600,
                        unfocusedContainerColor = if (isActive) White else Neutral50,
                        focusedContainerColor = White
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.weight(0.7f)
                ) {
                    OutlinedTextField(
                        value = question.type,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo", color = if (isActive) Primary600 else Neutral700, fontWeight = FontWeight.SemiBold) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Neutral300,
                            focusedBorderColor = Primary600,
                            unfocusedContainerColor = if (isActive) White else Neutral50,
                            focusedContainerColor = White
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Neutral900)
                    )
                    
                    ExposedDropdownMenu(
                        expanded = expanded, 
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Texto corto", "Texto largo", "Número", "Fecha", "Selección única", "Archivo").forEach { option ->
                            DropdownMenuItem(
                                text = { 
                                    Text(
                                        text = option, 
                                        color = Neutral900, 
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                                    ) 
                                },
                                onClick = { onTypeChange(option); expanded = false },
                                leadingIcon = { 
                                    Icon(
                                        imageVector = getTypeIcon(option), 
                                        contentDescription = null, 
                                        tint = Primary600, 
                                        modifier = Modifier.size(20.dp)
                                    ) 
                                }
                            )
                        }
                    }
                }
            }

            if (isActive) {
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = Primary200.copy(alpha = 0.5f))
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Duplicate Logic */ }) {
                        Icon(Icons.Rounded.ContentCopy, "Duplicar", tint = Neutral500, modifier = Modifier.size(20.dp))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Rounded.DeleteOutline, "Eliminar", tint = Neutral500, modifier = Modifier.size(22.dp))
                    }
                    
                    Spacer(Modifier.width(12.dp))
                    Box(modifier = Modifier.width(1.dp).height(24.dp).background(Neutral200))
                    Spacer(Modifier.width(20.dp))

                    Text("Obligatorio", style = MaterialTheme.typography.bodySmall, color = Neutral700, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    Switch(
                        checked = question.isRequired,
                        onCheckedChange = onRequiredToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White,
                            checkedTrackColor = Primary600,
                            uncheckedThumbColor = Neutral400,
                            uncheckedTrackColor = Neutral200
                        )
                    )
                }
            }
        }
    }
}

private fun getTypeIcon(type: String): ImageVector {
    return when (type) {
        "Texto corto" -> Icons.Rounded.ShortText
        "Texto largo" -> Icons.Rounded.Notes
        "Número" -> Icons.Rounded.Numbers
        "Fecha" -> Icons.Rounded.CalendarToday
        "Selección única" -> Icons.Rounded.RadioButtonChecked
        "Archivo" -> Icons.Rounded.AttachFile
        else -> Icons.Rounded.Help
    }
}

package com.example.autonomo.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun RegisterScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    var username       by remember { mutableStateOf("") }
    var firstName      by remember { mutableStateOf("") }
    var lastName       by remember { mutableStateOf("") }
    var email          by remember { mutableStateOf("") }
    var phone          by remember { mutableStateOf("") }
    var birthDate      by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var password       by remember { mutableStateOf("") }
    var confirmPass    by remember { mutableStateOf("") }
    var altEmail       by remember { mutableStateOf("") }
    var altPhone       by remember { mutableStateOf("") }
    var showPass       by remember { mutableStateOf(false) }
    var showConfPass   by remember { mutableStateOf(false) }
    var genderExpanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("Masculino", "Femenino", "No binario", "Prefiero no decir")

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Crear cuenta", onBack = onBack) },
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

            SectionLabel("Información requerida")

            AppTextField(
                value = username, onValueChange = { username = it },
                label = "Nombre de usuario", leadingIcon = Icons.Rounded.AlternateEmail
            )
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTextField(
                    value = firstName, onValueChange = { firstName = it },
                    label = "Nombres", modifier = Modifier.weight(1f)
                )
                AppTextField(
                    value = lastName, onValueChange = { lastName = it },
                    label = "Apellidos", modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = email, onValueChange = { email = it },
                label = "Correo principal", leadingIcon = Icons.Rounded.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = phone, onValueChange = { phone = it },
                label = "Celular principal", leadingIcon = Icons.Rounded.Phone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = birthDate, onValueChange = { birthDate = it },
                label = "Fecha de nacimiento (DD/MM/AAAA)", leadingIcon = Icons.Rounded.CalendarToday
            )
            Spacer(Modifier.height(14.dp))

            @OptIn(ExperimentalMaterial3Api::class)
            ExposedDropdownMenuBox(
                expanded         = genderExpanded,
                onExpandedChange = { genderExpanded = it }
            ) {
                OutlinedTextField(
                    value              = selectedGender,
                    onValueChange      = {},
                    readOnly           = true,
                    label              = { Text("Género") },
                    leadingIcon        = { Icon(Icons.Rounded.Wc, null, Modifier.size(20.dp)) },
                    trailingIcon       = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                    shape              = RoundedCornerShape(14.dp),
                    colors             = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary600,
                        focusedLabelColor  = Primary600,
                        unfocusedBorderColor = Neutral300
                    ),
                    modifier           = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = genderExpanded, onDismissRequest = { genderExpanded = false }) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text    = { Text(option, style = MaterialTheme.typography.bodyMedium) },
                            onClick = { selectedGender = option; genderExpanded = false }
                        )
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = password, onValueChange = { password = it },
                label = "Contraseña", leadingIcon = Icons.Rounded.Lock,
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingContent = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(if (showPass) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility, null, Modifier.size(20.dp))
                    }
                }
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = confirmPass, onValueChange = { confirmPass = it },
                label = "Confirmar contraseña", leadingIcon = Icons.Rounded.LockReset,
                visualTransformation = if (showConfPass) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPass.isNotEmpty() && confirmPass != password,
                errorMessage = "Las contraseñas no coinciden",
                trailingContent = {
                    IconButton(onClick = { showConfPass = !showConfPass }) {
                        Icon(if (showConfPass) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility, null, Modifier.size(20.dp))
                    }
                }
            )

            Spacer(Modifier.height(32.dp))
            PrimaryButton(
                text = "Continuar", onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text     = text,
        style    = MaterialTheme.typography.titleSmall,
        color    = Primary600,
        modifier = Modifier.padding(bottom = 14.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ServiConnectTheme {
        RegisterScreen(onContinue = {}, onBack = {})
    }
}

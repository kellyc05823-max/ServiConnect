package com.example.autonomo.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onBack: () -> Unit
) {
    var identifier by remember { mutableStateOf("") }
    var password   by remember { mutableStateOf("") }
    var showPass   by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ServiConnectTopBar(title = "", onBack = onBack)
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

            Text("Bienvenido\nde vuelta", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(8.dp))
            Text(
                "Ingresa tus credenciales para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(36.dp))

            AppTextField(
                value         = identifier,
                onValueChange = { identifier = it },
                label         = "Usuario, correo o teléfono",
                leadingIcon   = Icons.Rounded.Person
            )
            Spacer(Modifier.height(16.dp))
            AppTextField(
                value               = password,
                onValueChange       = { password = it },
                label               = "Contraseña",
                leadingIcon         = Icons.Rounded.Lock,
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions     = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingContent     = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector = if (showPass) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = if (showPass) "Ocultar" else "Mostrar",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )

            Spacer(Modifier.height(12.dp))
            TextButton(
                onClick       = onForgotPassword,
                contentPadding = PaddingValues(0.dp),
                modifier      = Modifier.align(Alignment.End)
            ) {
                Text("¿Olvidaste tu contraseña?", style = MaterialTheme.typography.labelMedium, color = Primary600)
            }

            Spacer(Modifier.height(32.dp))
            PrimaryButton(
                text     = "Iniciar Sesión",
                onClick  = onLoginSuccess,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment     = Alignment.CenterVertically,
                modifier              = Modifier.fillMaxWidth()
            ) {
                Text(
                    "¿No tienes cuenta? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(onClick = onNavigateToRegister, contentPadding = PaddingValues(0.dp)) {
                    Text("Regístrate", style = MaterialTheme.typography.labelLarge, color = Primary600)
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ServiConnectTheme {
        LoginScreen(
            onLoginSuccess = {},
            onForgotPassword = {},
            onNavigateToRegister = {},
            onBack = {}
        )
    }
}

package com.example.autonomo.ui.screens.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.autonomo.ui.components.AppTextField
import com.example.autonomo.ui.components.PrimaryButton
import com.example.autonomo.ui.components.ServiConnectTopBar

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val user = uiState.user

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            firstName = it.firstName
            lastName = it.lastName
            city = it.city
            province = it.province
            phone = it.phone
            description = it.description
        }
    }

    Scaffold(
        topBar = { ServiConnectTopBar(title = "Editar Perfil", onBack = onBack) }
    ) { padding ->
        if (uiState.isLoading && user == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(16.dp))
                
                AppTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombre")
                Spacer(Modifier.height(16.dp))
                AppTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellido")
                Spacer(Modifier.height(16.dp))
                AppTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono")
                Spacer(Modifier.height(16.dp))
                AppTextField(value = city, onValueChange = { city = it }, label = "Ciudad")
                Spacer(Modifier.height(16.dp))
                AppTextField(value = province, onValueChange = { province = it }, label = "Provincia")
                Spacer(Modifier.height(16.dp))
                AppTextField(
                    value = description, 
                    onValueChange = { description = it }, 
                    label = "Descripción / Bio",
                    singleLine = false,
                    minLines = 4
                )
                
                Spacer(Modifier.height(32.dp))
                PrimaryButton(
                    text = "Guardar Cambios",
                    onClick = {
                        viewModel.updateProfile(firstName, lastName, city, province, description, phone)
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

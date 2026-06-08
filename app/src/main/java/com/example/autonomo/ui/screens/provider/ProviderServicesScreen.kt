package com.example.autonomo.ui.screens.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.autonomo.data.MockData
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@Composable
fun ProviderServicesScreen(
    navController: NavController,
    onCreateServiceClick: () -> Unit,
    onServiceClick: (String) -> Unit
) {
    Scaffold(
        topBar = { ServiConnectTopBar(title = "Mis Servicios") },
        bottomBar = { ProviderBottomBar(navController) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateServiceClick,
                containerColor = Primary600,
                contentColor = White,
                icon = { Icon(Icons.Rounded.Add, null) },
                text = { Text("Nuevo Servicio") }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        val myServices = MockData.services.filter { it.providerName == "Carlos Mendoza" }

        if (myServices.isEmpty()) {
            EmptyStateView(
                icon = Icons.Rounded.WorkOutline,
                title = "No has publicado servicios",
                subtitle = "Comienza a ofrecer tus servicios para recibir solicitudes.",
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            )
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "Gestiona tus servicios",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            "Aquí puedes ver y editar los servicios que ofreces a tus clientes.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Neutral500
                        )
                    }
                }

                items(myServices) { service ->
                    ServiceRowItem(
                        service = service,
                        onClick = { onServiceClick(service.id) },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    HorizontalDivider(color = Neutral100, modifier = Modifier.padding(horizontal = 20.dp))
                }
                
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

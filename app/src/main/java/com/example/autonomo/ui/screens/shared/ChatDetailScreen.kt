package com.example.autonomo.ui.screens.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.components.*
import com.example.autonomo.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    contactName: String,
    serviceName: String,
    status: OrderStatus,
    onBack: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(
        "Hola, vi tu solicitud para el servicio de $serviceName." to false,
        "¡Excelente! ¿Cuándo podríamos empezar?" to true,
        "Podemos iniciar este lunes si te parece bien." to false
    ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(contactName, style = MaterialTheme.typography.titleMedium, color = Neutral900)
                        Text("$serviceName · ${status.label}", style = MaterialTheme.typography.labelSmall, color = Primary600)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Primary600,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .imePadding()
                ) {
                    AppTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = "Escribe un mensaje...",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(12.dp))
                    IconButton(
                        onClick = { 
                            if (message.isNotBlank()) {
                                messages.add(message to true)
                                message = ""
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Primary600, contentColor = White),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.Send, null, modifier = Modifier.size(20.dp))
                    }
                }
            }
        },
        containerColor = Neutral50
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item { Spacer(Modifier.height(16.dp)) }
            items(messages) { (text, isMine) ->
                ChatBubble(text = text, isMine = isMine)
            }
        }
    }
}

@Composable
private fun ChatBubble(text: String, isMine: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (isMine) Primary600 else White,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isMine) 16.dp else 0.dp,
                bottomEnd = if (isMine) 0.dp else 16.dp
            ),
            shadowElevation = if (isMine) 0.dp else 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isMine) White else Neutral900,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatDetailScreenPreview() {
    ServiConnectTheme {
        ChatDetailScreen(
            contactName = "Carlos Mendoza",
            serviceName = "Desarrollo Web",
            status = OrderStatus.IN_PROGRESS,
            onBack = {}
        )
    }
}

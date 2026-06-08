package com.example.autonomo.ui.screens.client

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.autonomo.data.MockData
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.components.*

@Composable
fun ClientChatsScreen(
    navController: NavController,
    onChatClick: (String, String, OrderStatus) -> Unit
) {
    Scaffold(
        topBar    = { ServiConnectTopBar(title = "Chats") },
        bottomBar = { ClientBottomBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (MockData.clientChats.isEmpty()) {
            EmptyStateView(
                icon     = Icons.Rounded.ChatBubbleOutline,
                title    = "Sin conversaciones",
                subtitle = "Aquí aparecerán tus chats con proveedores.",
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            )
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(MockData.clientChats, key = { it.id }) { chat ->
                    ChatListItem(
                        contactName     = chat.contactName,
                        contactAvatar   = chat.contactAvatar,
                        serviceRelated  = chat.serviceRelated,
                        orderStatus     = chat.orderStatus,
                        lastMessage     = chat.lastMessage,
                        lastMessageTime = chat.lastMessageTime,
                        unreadCount     = chat.unreadCount,
                        onClick         = { onChatClick(chat.contactName, chat.serviceRelated, chat.orderStatus) }
                    )
                }
            }
        }
    }
}

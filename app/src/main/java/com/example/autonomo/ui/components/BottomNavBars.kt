package com.example.autonomo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.autonomo.navigation.Screen
import com.example.autonomo.ui.theme.Neutral400
import com.example.autonomo.ui.theme.Primary600
import com.example.autonomo.ui.theme.White

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
)

private val clientNavItems = listOf(
    BottomNavItem("Inicio",   Icons.Rounded.HomeWork,      Icons.Rounded.HomeWork,       Screen.ClientHome.route),
    BottomNavItem("Pedidos",  Icons.Rounded.Receipt,       Icons.Rounded.Receipt,        Screen.ClientOrders.route),
    BottomNavItem("Chats",    Icons.Rounded.ChatBubbleOutline, Icons.Rounded.ChatBubble, Screen.ClientChats.route),
    BottomNavItem("Perfil",   Icons.Rounded.PersonOutline, Icons.Rounded.Person,         Screen.ClientProfile.route),
)

private val providerNavItems = listOf(
    BottomNavItem("Inicio",    Icons.Rounded.Dashboard,       Icons.Rounded.Dashboard,        Screen.ProviderDashboard.route),
    BottomNavItem("Servicios", Icons.Rounded.WorkOutline,     Icons.Rounded.Work,             Screen.ProviderServices.route),
    BottomNavItem("Chats",     Icons.Rounded.ChatBubbleOutline, Icons.Rounded.ChatBubble,     Screen.ProviderChats.route),
    BottomNavItem("Perfil",    Icons.Rounded.PersonOutline,   Icons.Rounded.Person,           Screen.ProviderProfile.route),
)

@Composable
fun ClientBottomBar(navController: NavController) {
    BottomNavBar(navController = navController, items = clientNavItems)
}

@Composable
fun ProviderBottomBar(navController: NavController) {
    BottomNavBar(navController = navController, items = providerNavItems)
}

@Composable
private fun BottomNavBar(navController: NavController, items: List<BottomNavItem>) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute   = backStackEntry?.destination?.route

    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected   = isSelected,
                onClick    = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = item.label
                    )
                },
                label  = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Primary600,
                    selectedTextColor   = Primary600,
                    unselectedIconColor = Neutral400,
                    unselectedTextColor = Neutral400,
                    indicatorColor      = Primary600.copy(alpha = 0.12f)
                )
            )
        }
    }
}

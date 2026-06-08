package com.example.autonomo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.screens.auth.InitialLocationScreen
import com.example.autonomo.ui.screens.auth.LoginScreen
import com.example.autonomo.ui.screens.auth.RegisterScreen
import com.example.autonomo.ui.screens.auth.RoleSelectionScreen
import com.example.autonomo.ui.screens.auth.WelcomeScreen
import com.example.autonomo.ui.screens.client.ClientChatsScreen
import com.example.autonomo.ui.screens.client.ClientHomeScreen
import com.example.autonomo.ui.screens.client.ClientOrdersScreen
import com.example.autonomo.ui.screens.client.ClientProfileScreen
import com.example.autonomo.ui.screens.client.OrderDetailScreen
import com.example.autonomo.ui.screens.client.ServiceDetailScreen
import com.example.autonomo.ui.screens.client.ServiceRequestScreen
import com.example.autonomo.ui.screens.provider.*
import com.example.autonomo.ui.screens.shared.ChatDetailScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController    = navController,
        startDestination = Screen.Welcome.route
    ) {

        // ── Auth ──────────────────────────────────────────────────────────────
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick    = { navController.navigate(Screen.Login.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess      = { navController.navigate(Screen.RoleSelection.route) { popUpTo(Screen.Welcome.route) { inclusive = false } } },
                onForgotPassword    = { /* future */ },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onBack              = { navController.popBackStack() }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onContinue = { navController.navigate(Screen.InitialLocation.route) },
                onBack     = { navController.popBackStack() }
            )
        }

        composable(Screen.InitialLocation.route) {
            InitialLocationScreen(
                onSaveLocation = { navController.navigate(Screen.RoleSelection.route) },
                onSkip         = { navController.navigate(Screen.RoleSelection.route) },
                onBack         = { navController.popBackStack() }
            )
        }

        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                onClientSelected   = {
                    navController.navigate(Screen.ClientHome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onProviderSelected = {
                    navController.navigate(Screen.ProviderDashboard.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Client ────────────────────────────────────────────────────────────
        composable(Screen.ClientHome.route) {
            ClientHomeScreen(
                navController  = navController,
                onServiceClick = { id -> navController.navigate(Screen.ServiceDetail.createRoute(id)) }
            )
        }

        composable(
            route     = Screen.ServiceDetail.route,
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("serviceId") ?: ""
            ServiceDetailScreen(
                serviceId    = id,
                onRequestClick = { navController.navigate(Screen.ServiceRequest.createRoute(id)) },
                onBack       = { navController.popBackStack() }
            )
        }

        composable(
            route     = Screen.ServiceRequest.route,
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("serviceId") ?: ""
            ServiceRequestScreen(
                serviceId = id,
                onSubmit  = { navController.navigate(Screen.ClientOrders.route) { popUpTo(Screen.ClientHome.route) } },
                onBack    = { navController.popBackStack() }
            )
        }

        composable(Screen.ClientOrders.route) {
            ClientOrdersScreen(
                navController  = navController,
                onOrderClick   = { id -> navController.navigate(Screen.OrderDetail.createRoute(id)) }
            )
        }

        composable(
            route     = Screen.OrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderDetailScreen(
                orderId = id,
                onChatClick = { name, service, status -> 
                    navController.navigate(Screen.ChatDetail.createRoute(name, service, status.name))
                },
                onBack  = { navController.popBackStack() }
            )
        }

        composable(Screen.ClientChats.route) {
            ClientChatsScreen(
                navController = navController,
                onChatClick = { name, service, status -> 
                    navController.navigate(Screen.ChatDetail.createRoute(name, service, status.name))
                }
            )
        }

        composable(Screen.ClientProfile.route) {
            ClientProfileScreen(
                navController = navController,
                onLogout      = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── Provider ──────────────────────────────────────────────────────────
        composable(Screen.ProviderDashboard.route) {
            ProviderDashboardScreen(
                navController      = navController,
                onCardClick        = { filter -> 
                    navController.navigate(Screen.RequestManagement.createRoute(filter))
                }
            )
        }

        composable(
            route     = Screen.RequestManagement.route,
            arguments = listOf(navArgument("filter") { type = NavType.StringType; defaultValue = "Todas" })
        ) { backStackEntry ->
            val filter = backStackEntry.arguments?.getString("filter") ?: "Todas"
            RequestManagementScreen(
                navController   = navController,
                initialFilter   = filter,
                onRequestClick  = { id -> navController.navigate(Screen.RequestDetail.createRoute(id)) },
                onBack          = { navController.popBackStack() }
            )
        }

        composable(
            route     = Screen.RequestDetail.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("requestId") ?: ""
            RequestDetailScreen(
                requestId = id,
                onBack    = { navController.popBackStack() }
            )
        }

        composable(Screen.ProviderServices.route) {
            ProviderServicesScreen(
                navController = navController,
                onCreateServiceClick = { navController.navigate(Screen.CreateServiceBasic.route) },
                onServiceClick = { id -> navController.navigate(Screen.ProviderServiceDetail.createRoute(id)) }
            )
        }

        composable(
            route = Screen.ProviderServiceDetail.route,
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("serviceId") ?: ""
            ProviderServiceDetailScreen(
                serviceId = id,
                onBack = { navController.popBackStack() },
                onEditBasic = { navController.navigate(Screen.CreateServiceBasic.route) },
                onEditForm = { navController.navigate(Screen.CreateServiceForm.route) },
                onEditPricing = { navController.navigate(Screen.CreateServiceExtra.route) }
            )
        }

        composable(Screen.CreateServiceBasic.route) {
            CreateServiceBasicScreen(
                onNext = { navController.navigate(Screen.CreateServiceForm.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CreateServiceForm.route) {
            CreateServiceFormScreen(
                onNext = { navController.navigate(Screen.CreateServiceExtra.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CreateServiceExtra.route) {
            CreateServiceExtraScreen(
                onPublish = {
                    navController.navigate(Screen.ProviderDashboard.route) {
                        popUpTo(Screen.CreateServiceBasic.route) { inclusive = true }
                    }
                },
                onBack    = { navController.popBackStack() }
            )
        }

        composable(Screen.ProviderChats.route) {
            ProviderChatsScreen(
                navController = navController,
                onChatClick = { name, service, status -> 
                    navController.navigate(Screen.ChatDetail.createRoute(name, service, status.name))
                }
            )
        }

        composable(Screen.ProviderProfile.route) {
            ProviderProfileScreen(
                navController = navController,
                onLogout      = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── Shared ───────────────────────────────────────────────────────────
        composable(
            route = Screen.ChatDetail.route,
            arguments = listOf(
                navArgument("contactName") { type = NavType.StringType },
                navArgument("serviceName") { type = NavType.StringType },
                navArgument("status") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("contactName") ?: ""
            val service = backStackEntry.arguments?.getString("serviceName") ?: ""
            val statusStr = backStackEntry.arguments?.getString("status") ?: "NEW"
            val status = try { OrderStatus.valueOf(statusStr) } catch(e: Exception) { OrderStatus.NEW }
            
            ChatDetailScreen(
                contactName = name,
                serviceName = service,
                status = status,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

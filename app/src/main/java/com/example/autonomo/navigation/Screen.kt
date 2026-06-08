package com.example.autonomo.navigation

/**
 * Sealed class that holds every navigation route in the app.
 * Organized by authentication flow, client flow and provider flow.
 */
sealed class Screen(val route: String) {

    // ─── Auth flow ────────────────────────────────────────────────────────────
    data object Welcome        : Screen("welcome")
    data object Login          : Screen("login")
    data object Register       : Screen("register")
    data object InitialLocation: Screen("initial_location")
    data object RoleSelection  : Screen("role_selection")

    // ─── Client flow ──────────────────────────────────────────────────────────
    data object ClientHome     : Screen("client_home")
    data object ServiceDetail  : Screen("service_detail/{serviceId}") {
        fun createRoute(serviceId: String) = "service_detail/$serviceId"
    }
    data object ServiceRequest : Screen("service_request/{serviceId}") {
        fun createRoute(serviceId: String) = "service_request/$serviceId"
    }
    data object ClientOrders   : Screen("client_orders")
    data object OrderDetail    : Screen("order_detail/{orderId}") {
        fun createRoute(orderId: String) = "order_detail/$orderId"
    }
    data object ClientChats    : Screen("client_chats")
    data object ClientProfile  : Screen("client_profile")

    // ─── Provider flow ────────────────────────────────────────────────────────
    data object ProviderDashboard  : Screen("provider_dashboard")
    data object RequestManagement  : Screen("request_management/{filter}") {
        fun createRoute(filter: String = "all") = "request_management/$filter"
    }
    data object RequestDetail      : Screen("request_detail/{requestId}") {
        fun createRoute(requestId: String) = "request_detail/$requestId"
    }
    data object ProviderServices   : Screen("provider_services")
    data object ProviderServiceDetail : Screen("provider_service_detail/{serviceId}") {
        fun createRoute(serviceId: String) = "provider_service_detail/$serviceId"
    }
    data object CreateServiceBasic : Screen("create_service_basic")
    data object CreateServiceForm  : Screen("create_service_form")
    data object CreateServiceExtra : Screen("create_service_extra")
    data object ProviderChats      : Screen("provider_chats")
    data object ProviderProfile    : Screen("provider_profile")

    // ─── Shared flow ─────────────────────────────────────────────────────────
    data object ChatDetail : Screen("chat_detail/{contactName}/{serviceName}/{status}") {
        fun createRoute(contactName: String, serviceName: String, status: String) = 
            "chat_detail/$contactName/$serviceName/$status"
    }
}

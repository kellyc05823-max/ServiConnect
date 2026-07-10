package com.example.autonomo.data

enum class OrderStatus(val label: String, val colorKey: String) {
    NEW("Nueva", "info"),
    PENDING("Pendiente", "warning"),
    IN_PROGRESS("En Proceso", "primary"),
    IN_REVIEW("En Revisión", "secondary"),
    COMPLETED("Completada", "success"),
    REJECTED("Rechazada", "error")
}

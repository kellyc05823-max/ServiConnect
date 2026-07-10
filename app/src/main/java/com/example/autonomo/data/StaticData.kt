package com.example.autonomo.data

data class ServiceCategory(
    val id: String,
    val name: String,
    val icon: String
)

object StaticData {
    val categories = listOf(
        ServiceCategory("cat1", "Tecnología",    "computer"),
        ServiceCategory("cat2", "Hogar",         "home"),
        ServiceCategory("cat3", "Salud",         "health_and_safety"),
        ServiceCategory("cat4", "Educación",     "school"),
        ServiceCategory("cat5", "Legal",         "gavel"),
        ServiceCategory("cat6", "Diseño",        "palette"),
        ServiceCategory("cat7", "Transporte",    "directions_car"),
        ServiceCategory("cat8", "Mascotas",      "pets"),
    )
}

package com.example.autonomo.data

// ─── Models ──────────────────────────────────────────────────────────────────

data class ServiceCategory(
    val id: String,
    val name: String,
    val icon: String          // Material icon name (used with Icons.Rounded)
)

data class ServiceItem(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val fullDescription: String,
    val providerName: String,
    val providerAvatar: String,   // initials fallback
    val imageColor: Long,         // color for placeholder image
    val rating: Float,
    val reviewCount: Int,
    val price: String,
    val schedule: String,
    val paymentMethods: List<String>,
    val warranty: String,
    val estimatedTime: String,
    val tags: List<String>
)

data class OrderItem(
    val id: String,
    val serviceName: String,
    val providerName: String,
    val status: OrderStatus,
    val date: String,
    val serviceId: String
)

enum class OrderStatus(val label: String, val colorKey: String) {
    NEW("Nueva", "info"),
    PENDING("Pendiente", "warning"),
    IN_PROGRESS("En Proceso", "primary"),
    IN_REVIEW("En Revisión", "secondary"),
    COMPLETED("Completada", "success"),
    REJECTED("Rechazada", "error")
}

data class ChatItem(
    val id: String,
    val contactName: String,
    val contactAvatar: String,
    val serviceRelated: String,
    val orderStatus: OrderStatus,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int
)

data class FormField(
    val id: String,
    val label: String,
    val type: FormFieldType,
    val required: Boolean,
    val options: List<String> = emptyList()
)

enum class FormFieldType { SHORT_TEXT, LONG_TEXT, NUMBER, DATE, SINGLE_SELECT, MULTI_SELECT, FILE }

// ─── Mock Data ───────────────────────────────────────────────────────────────

object MockData {

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

    val services = listOf(
        ServiceItem(
            id              = "s1",
            name            = "Desarrollo Web Profesional",
            category        = "Tecnología",
            description     = "Sitios web modernos, rápidos y adaptables a cualquier dispositivo.",
            fullDescription = "Ofrezco desarrollo de sitios web con tecnologías modernas: React, Next.js, Tailwind CSS y backend en Node.js o Python. Especializado en landing pages, tiendas en línea y aplicaciones web a medida. Incluye diseño UX/UI, optimización SEO y soporte post-entrega.",
            providerName    = "Carlos Mendoza",
            providerAvatar  = "CM",
            imageColor      = 0xFF145050,
            rating          = 4.9f,
            reviewCount     = 127,
            price           = "Desde \$150",
            schedule        = "Lunes a Viernes 9:00 – 18:00",
            paymentMethods  = listOf("Transferencia", "PayPal", "Tarjeta de crédito"),
            warranty        = "30 días de soporte gratuito post-entrega",
            estimatedTime   = "7 – 21 días hábiles",
            tags            = listOf("Web", "React", "SEO")
        ),
        ServiceItem(
            id              = "s2",
            name            = "Diseño de Logotipos & Branding",
            category        = "Diseño",
            description     = "Identidad visual única que comunica la esencia de tu negocio.",
            fullDescription = "Creación de logotipos vectoriales, manual de identidad corporativa, paletas de colores y tipografía. Entrego archivos en AI, PDF, PNG y SVG listos para imprenta y digital. Hasta 3 rondas de revisión incluidas.",
            providerName    = "Ana Lucía Torres",
            providerAvatar  = "AT",
            imageColor      = 0xFF6B3FA0,
            rating          = 4.8f,
            reviewCount     = 89,
            price           = "Desde \$80",
            schedule        = "Lunes a Sábado 8:00 – 17:00",
            paymentMethods  = listOf("Transferencia", "Efectivo"),
            warranty        = "2 revisiones adicionales gratuitas en 15 días",
            estimatedTime   = "3 – 7 días hábiles",
            tags            = listOf("Logo", "Branding", "Vector")
        ),
        ServiceItem(
            id              = "s3",
            name            = "Clases de Inglés Personalizadas",
            category        = "Educación",
            description     = "Aprende inglés a tu ritmo con metodología comunicativa y resultados reales.",
            fullDescription = "Clases 1 a 1 o en grupos pequeños adaptadas a tu nivel y objetivos. Certificación Cambridge, inglés de negocios, conversación fluida o preparación para viajes. Material incluido. Plataforma Zoom o presencial en Guayaquil.",
            providerName    = "Sofía Ramírez",
            providerAvatar  = "SR",
            imageColor      = 0xFF1A6B6B,
            rating          = 5.0f,
            reviewCount     = 204,
            price           = "\$15 / hora",
            schedule        = "Flexible, previa coordinación",
            paymentMethods  = listOf("Transferencia", "Efectivo"),
            warranty        = "Primera sesión de prueba sin costo",
            estimatedTime   = "Resultados visibles en 4 semanas",
            tags            = listOf("Inglés", "Online", "Presencial")
        ),
        ServiceItem(
            id              = "s4",
            name            = "Reparación de Electrodomésticos",
            category        = "Hogar",
            description     = "Diagnóstico y reparación en el día para lavadoras, neveras y más.",
            fullDescription = "Técnico certificado con 12 años de experiencia. Reparación de lavadoras, secadoras, neveras, aires acondicionados y microondas. Visita a domicilio en Guayaquil y zonas cercanas. Repuestos originales con garantía.",
            providerName    = "Roberto Salinas",
            providerAvatar  = "RS",
            imageColor      = 0xFFB45309,
            rating          = 4.7f,
            reviewCount     = 312,
            price           = "Diagnóstico \$10 + mano de obra",
            schedule        = "Lunes a Domingo 8:00 – 20:00",
            paymentMethods  = listOf("Efectivo", "Transferencia", "Depósito"),
            warranty        = "90 días en repuestos y mano de obra",
            estimatedTime   = "Mismo día o siguiente hábil",
            tags            = listOf("Hogar", "Domicilio", "Urgente")
        ),
        ServiceItem(
            id              = "s5",
            name            = "Asesoría Legal Empresarial",
            category        = "Legal",
            description     = "Protege tu empresa con asesoría jurídica experta y accesible.",
            fullDescription = "Consultoría en constitución de compañías, contratos mercantiles, propiedad intelectual, derecho laboral y tributario. Primera consulta gratuita. Atención virtual y presencial en Guayaquil.",
            providerName    = "Dr. Mateo Andrade",
            providerAvatar  = "MA",
            imageColor      = 0xFF1E3A5F,
            rating          = 4.9f,
            reviewCount     = 58,
            price           = "\$60 / hora",
            schedule        = "Lunes a Viernes 9:00 – 17:00",
            paymentMethods  = listOf("Transferencia", "Tarjeta de crédito"),
            warranty        = "Confidencialidad garantizada",
            estimatedTime   = "Según complejidad del caso",
            tags            = listOf("Legal", "Empresa", "Contratos")
        ),
        ServiceItem(
            id              = "s6",
            name            = "Cuidado y Adiestramiento de Mascotas",
            category        = "Mascotas",
            description     = "Adiestramiento positivo y guardería para perros y gatos.",
            fullDescription = "Ofrezco adiestramiento básico y avanzado, guardería diurna y nocturna, paseos y baño/estética. Método de refuerzo positivo. Sin jaulas. Máximo 4 mascotas por sesión para atención personalizada.",
            providerName    = "Valentina Cruz",
            providerAvatar  = "VC",
            imageColor      = 0xFF854D0E,
            rating          = 4.8f,
            reviewCount     = 143,
            price           = "Desde \$25 / sesión",
            schedule        = "Todos los días 7:00 – 19:00",
            paymentMethods  = listOf("Efectivo", "Transferencia"),
            warranty        = "Sin costo si no hay progreso en 3 sesiones",
            estimatedTime   = "Resultados en 4 – 8 sesiones",
            tags            = listOf("Mascotas", "Perros", "Gatos")
        ),
    )

    val clientOrders = listOf(
        OrderItem("o1", "Desarrollo Web Profesional", "Carlos Mendoza", OrderStatus.IN_PROGRESS, "03 Jun 2025", "s1"),
        OrderItem("o2", "Diseño de Logotipos & Branding", "Ana Lucía Torres", OrderStatus.PENDING, "28 May 2025", "s2"),
        OrderItem("o3", "Clases de Inglés Personalizadas", "Sofía Ramírez", OrderStatus.COMPLETED, "10 May 2025", "s3"),
        OrderItem("o4", "Reparación de Electrodomésticos", "Roberto Salinas", OrderStatus.REJECTED, "15 May 2025", "s4"),
        OrderItem("o5", "Asesoría Legal Empresarial", "Dr. Mateo Andrade", OrderStatus.IN_REVIEW, "01 Jun 2025", "s5"),
        OrderItem("o6", "Cuidado de Mascotas", "Valentina Cruz", OrderStatus.NEW, "06 Jun 2025", "s6"),
    )

    val clientChats = listOf(
        ChatItem("ch1", "Carlos Mendoza", "CM", "Desarrollo Web Profesional", OrderStatus.IN_PROGRESS, "¿Puedes enviarme los accesos del hosting?", "10:32", 2),
        ChatItem("ch2", "Ana Lucía Torres", "AT", "Diseño de Logotipos", OrderStatus.PENDING, "Revisé la primera propuesta, me gustó mucho el concepto.", "Ayer", 0),
        ChatItem("ch3", "Sofía Ramírez", "SR", "Clases de Inglés", OrderStatus.COMPLETED, "¡Gracias por todo! Fue un placer.", "Dom", 0),
        ChatItem("ch4", "Roberto Salinas", "RS", "Reparación Electrodomésticos", OrderStatus.REJECTED, "Disculpa, no contábamos con el repuesto.", "Lun", 0),
    )

    val providerRequests = listOf(
        OrderItem("r1", "Desarrollo Web", "María González", OrderStatus.NEW, "06 Jun 2025", "s1"),
        OrderItem("r2", "Diseño de Logo", "Pedro Infante", OrderStatus.PENDING, "05 Jun 2025", "s2"),
        OrderItem("r3", "App Móvil", "Lucia Vera", OrderStatus.IN_PROGRESS, "01 Jun 2025", "s1"),
        OrderItem("r4", "Landing Page", "Jorge Muñoz", OrderStatus.IN_REVIEW, "28 May 2025", "s1"),
        OrderItem("r5", "Branding Completo", "Sandra Lima", OrderStatus.COMPLETED, "10 May 2025", "s2"),
        OrderItem("r6", "Rediseño Web", "Andrés Pinto", OrderStatus.REJECTED, "05 May 2025", "s1"),
    )

    val providerChats = listOf(
        ChatItem("pc1", "María González", "MG", "Desarrollo Web", OrderStatus.NEW, "Hola, ¿cuándo podemos iniciar?", "11:05", 1),
        ChatItem("pc2", "Pedro Infante", "PI", "Diseño de Logo", OrderStatus.PENDING, "Adjunto te envié la referencia visual.", "Ayer", 3),
        ChatItem("pc3", "Lucia Vera", "LV", "App Móvil", OrderStatus.IN_PROGRESS, "Revisé el avance, luce increíble.", "Lun", 0),
        ChatItem("pc4", "Sandra Lima", "SL", "Branding Completo", OrderStatus.COMPLETED, "¡Excelente trabajo! 5 estrellas.", "Dom", 0),
    )

    val serviceFormFields = listOf(
        FormField("f1", "¿Qué tipo de sitio necesitas?", FormFieldType.SINGLE_SELECT, true, listOf("Landing Page", "E-commerce", "Blog", "Aplicación web", "Otro")),
        FormField("f2", "Describe tu negocio brevemente", FormFieldType.SHORT_TEXT, true),
        FormField("f3", "¿Tienes diseño o referencia visual?", FormFieldType.SINGLE_SELECT, true, listOf("Sí, tengo un diseño", "Sí, tengo referencias", "No, necesito diseño desde cero")),
        FormField("f4", "Cuéntanos los detalles y requisitos", FormFieldType.LONG_TEXT, true),
        FormField("f5", "¿Cuál es tu presupuesto aproximado (USD)?", FormFieldType.NUMBER, false),
        FormField("f6", "Fecha límite deseada de entrega", FormFieldType.DATE, false),
        FormField("f7", "Adjuntar archivos o referencias (opcional)", FormFieldType.FILE, false),
    )
}

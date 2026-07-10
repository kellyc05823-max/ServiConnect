package com.example.autonomo.data.local

import androidx.room.TypeConverter
import com.example.autonomo.data.OrderStatus

class Converters {
    @TypeConverter
    fun fromOrderStatus(status: OrderStatus): String {
        return status.name
    }

    @TypeConverter
    fun toOrderStatus(name: String): OrderStatus {
        return OrderStatus.valueOf(name)
    }
}

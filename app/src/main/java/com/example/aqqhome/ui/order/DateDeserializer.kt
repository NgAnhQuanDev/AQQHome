package com.example.aqqhome.ui.order

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DateDeserializer : JsonDeserializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
        return try {
            dateFormat.parse(json?.asString)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

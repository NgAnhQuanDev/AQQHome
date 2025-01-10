package com.example.aqqhome.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import android.util.Base64

class JWTDecoder(jwt: String) {

    private val payload: JsonObject?

    init {
        payload = decodeJWTPayload(jwt)
    }

    fun getPayload(): JsonObject? {
        return payload
    }

    private fun decodeJWTPayload(jwt: String): JsonObject? {
        val parts = jwt.split(".")
        if (parts.size != 3) {
            return null
        }

        try {
            val payloadString = parts[1]
            val decodedBytes = Base64.decode(payloadString, Base64.URL_SAFE or Base64.NO_PADDING)
            val payloadJson = String(decodedBytes, Charsets.UTF_8)

            val gson = Gson()
            return gson.fromJson(payloadJson, JsonObject::class.java)
        } catch (e: IllegalArgumentException) {
            // Base64 decoding error
            return null
        } catch (e: JsonParseException) {
            // Json parsing error
            return null
        }
    }
}

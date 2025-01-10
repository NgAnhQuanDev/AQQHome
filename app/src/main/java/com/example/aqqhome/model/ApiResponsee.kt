package com.example.aqqhome.model

data class ApiResponsee(
    val success: Boolean,
    val message: String,
    val records: List<String> // Assuming records is a list of strings as per your JSON structure
)
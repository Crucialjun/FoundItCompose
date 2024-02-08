package com.example.foundit.services.shared_preferences_service

interface SharedPreferenceService {
    suspend fun saveData(key: String, value: String)
    suspend fun getData(key: String, defaultValue: String): String
}
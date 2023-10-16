package com.example.foundit.services.shared_preferences_service

interface SharedPreferenceService {
    fun saveData(key: String, value: String)
    fun getData(key: String, defaultValue: String): String
}
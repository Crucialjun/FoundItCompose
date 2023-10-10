package com.example.foundit.services.db_service

import com.example.foundit.core.app.models.AppUser

interface DbService {
    suspend fun addAppUserToDb(appUser: AppUser)
    suspend fun retrieveAppUserFromDb(uid : String) : AppUser?
}
package com.example.foundit.services.db_service

import com.example.foundit.core.app.models.AppUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.tasks.await

class DbServiceImpl : DbService {
    private val db = Firebase.firestore

    override suspend fun addAppUserToDb(appUser: AppUser) {
        try{
            Logger.d("ading $appUser to db")
            db.collection("users").document(appUser.uid).set(appUser)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateUserProfile(
        fullName: String,
        username: String,
        phoneNumber: String,
        uid: String
    ): AppUser? {
        try {
            val user = db.collection("users").document(uid).get().await()
            val appUser = user.toObject(AppUser::class.java)
            appUser?.let {
                appUser.name = fullName
                appUser.username = username
                appUser.phoneNumber = phoneNumber
                db.collection("users").document(uid).set(appUser)
                return appUser
            }
            return null
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun retrieveAppUserFromDb(uid: String): AppUser? {
        try {
            val user = db.collection("users").document(uid).get().await()
            return user.toObject(AppUser::class.java)
        } catch (e: Exception) {
            throw e
        }
    }
}
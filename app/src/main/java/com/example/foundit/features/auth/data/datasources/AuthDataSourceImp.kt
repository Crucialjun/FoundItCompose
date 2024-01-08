package com.example.foundit.features.auth.data.datasources


import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.example.foundit.services.auth_service.AuthService
import com.example.foundit.services.db_service.DbService
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseUser
import com.orhanobut.logger.Logger
import javax.inject.Inject


class AuthDataSourceImp @Inject constructor(
    private val authService: AuthService,
    private val dbService: DbService
) : AuthDataSource {


    override suspend fun loginWithGoogle(oneTap: SignInClient): IntentSender {
        return try {
            authService.loginWithGoogle(oneTap)
        } catch (e: Exception) {
            print(e.toString())
            throw e
        }
    }

    override suspend fun signOut() {
        try {
            //oneTapClient.signOut().await()
            authService.signOut()
        } catch (e: Exception) {
            print(e.toString())
            throw e
        }
    }

    override suspend fun getSignedInUser(): FirebaseUser? {
        try {
            return authService.getSignedInUser()
        } catch (e: Exception) {
            print(e.toString())
            throw e
        }
    }


    override
    suspend fun signInWithIntent(params: LoginWithIntentParams): AppUser {
        try {
            val firebaseUser = authService.signInWithIntent(params)
            Logger.d(firebaseUser.toString())

            val savedAppUser = dbService.retrieveAppUserFromDb(firebaseUser!!.uid)
            return if (savedAppUser != null) {
                Logger.d("user already exists,${savedAppUser}}")
                savedAppUser
            } else {
                val appUser: AppUser = AppUser(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: "",
                    profilePicUrl = firebaseUser.photoUrl.toString(),
                )

                dbService.addAppUserToDb(appUser)
                appUser
            }


        } catch (e: Exception) {
            print(e.toString())
            throw e
        }
    }

    override suspend fun registerWithEmail(
        email: String,
        password: String,
        username: String
    ): Either<Failure, AppUser?> {

        val res: Either<Failure, FirebaseUser?> = authService.registerWithEmail(email, password)

        return res.fold<Either<Failure, AppUser?>>(
            ifLeft = {
                return Either.Left(Failure(it.message ?: "An error occurred"))
            }
        ) {
            val appUser: AppUser = AppUser(
                uid = it?.uid ?: "",
                email = it?.email ?: "",
                name = username,
                profilePicUrl = it?.photoUrl.toString(),
            )

            Logger.d("Register with email success,with${it?.email}}")
            dbService.addAppUserToDb(appUser)
            return Either.Right(appUser)
        }

        
    }


    //private val googleSignInClient = GoogleSignIn.getClient( gso)

}
package com.example.foundit.di

import android.content.Context
import com.example.foundit.features.auth.data.datasources.AuthDataSource
import com.example.foundit.features.auth.data.datasources.AuthDataSourceImp
import com.example.foundit.features.auth.data.repository.AuthRepository
import com.example.foundit.features.auth.data.repository.AuthRepositoryImp
import com.example.foundit.services.auth_service.AuthService
import com.example.foundit.services.auth_service.AuthServiceImpl
import com.example.foundit.services.db_service.DbService
import com.example.foundit.services.db_service.DbServiceImpl
import com.example.foundit.services.shared_preferences_service.SharedPreferenceService
import com.example.foundit.services.shared_preferences_service.SharedPreferenceServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ): AuthRepository {
        return AuthRepositoryImp(authDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(
        authService: AuthService,
        dbService: DbService
    ): AuthDataSource {
        return AuthDataSourceImp(
            authService,dbService
        )
    }

    @Provides
    @Singleton
    fun provideAuthService(
    ): AuthService {
        return AuthServiceImpl()
    }

    @Provides
    @Singleton
    fun provideDbService(

    ): DbService {
        return DbServiceImpl()
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceService(
        @ApplicationContext context: Context
    ): SharedPreferenceService {
        return SharedPreferenceServiceImpl(context)
    }


}
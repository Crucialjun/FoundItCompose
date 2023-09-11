package com.example.foundit.di

import android.content.Context
import com.example.foundit.features.auth.data.datasources.AuthDataSource
import com.example.foundit.features.auth.data.datasources.AuthDataSourceImp
import com.example.foundit.features.auth.data.repository.AuthRepository
import com.example.foundit.features.auth.data.repository.AuthRepositoryImp
import com.google.android.gms.auth.api.identity.SignInClient
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
        @ApplicationContext
        context: Context,
    ): AuthDataSource {
        return AuthDataSourceImp(
            context
        )
    }
}
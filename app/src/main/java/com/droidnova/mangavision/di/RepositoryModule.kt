package com.droidnova.mangavision.di

import android.app.Application
import android.content.Context
import com.droidnova.mangavision.data.local.dao.MangaDao
import com.droidnova.mangavision.data.local.dao.UserDao
import com.droidnova.mangavision.data.remote.api.MangaApi
import com.droidnova.mangavision.data.repository.AuthRepository
import com.droidnova.mangavision.data.repository.MangaRepository
import com.droidnova.mangavision.data.repositoryImpl.AuthRepositoryImpl
import com.droidnova.mangavision.data.repositoryImpl.MangaRepositoryImpl
import com.droidnova.mangavision.domain.usecase.AuthUseCases
import com.droidnova.mangavision.domain.usecase.GetLoggedInUserUseCase
import com.droidnova.mangavision.domain.usecase.GetPagedMangaUseCase
import com.droidnova.mangavision.domain.usecase.LoginOrRegisterUseCase
import com.droidnova.mangavision.domain.usecase.LogoutUseCase
import com.droidnova.mangavision.domain.usecase.MangaUseCases
import com.droidnova.mangavision.utils.UserSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        userDao: UserDao,
        sessionManager: UserSessionManager
    ): AuthRepository = AuthRepositoryImpl(userDao, sessionManager)

    @Provides
    @Singleton
    fun provideAuthUseCases(repo: AuthRepository): AuthUseCases {
        return AuthUseCases(
            loginOrRegister = LoginOrRegisterUseCase(repo),
            getLoggedInUser = GetLoggedInUserUseCase(repo),
            logout = LogoutUseCase(repo)
        )
    }

    @Provides
    @Singleton
    fun provideMangaUseCases(repo: MangaRepository): MangaUseCases {
        return MangaUseCases(
            getPagedManga = GetPagedMangaUseCase(repo)
        )

    }

    @Provides
    @Singleton
    fun provideMangaRepository(
        mangaApi: MangaApi,
        mangaDao: MangaDao,
        @ApplicationContext context: Context
    ):MangaRepository = MangaRepositoryImpl(mangaApi,mangaDao,context)
}

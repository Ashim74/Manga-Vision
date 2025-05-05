package com.droidnova.mangavision.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.droidnova.mangavision.data.local.dao.MangaDao
import com.droidnova.mangavision.data.local.dao.UserDao
import com.droidnova.mangavision.data.local.db.MangaDatabase
import com.droidnova.mangavision.data.remote.api.MangaApi
import com.droidnova.mangavision.utils.UserSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MangaDatabase =
        Room.databaseBuilder(app, MangaDatabase::class.java, "manga_db").build()

    @Provides
    fun provideUserDao(db: MangaDatabase): UserDao = db.userDao

    @Provides
    fun provideUserSessionManager(@ApplicationContext context: Context): UserSessionManager {
        return UserSessionManager(context)
    }

    @Provides
    fun provideMangaDao(db: MangaDatabase): MangaDao = db.mangaDao

    @Provides
    @Singleton
    fun provideMangaApi(): MangaApi {
        return Retrofit.Builder()
            .baseUrl("https://mangaverse-api.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("x-rapidapi-key", "1acb0c1251msh8da1f51b43ce538p1dceb8jsn73851971253f")
                        .addHeader("x-tpi-host", "mangaverse-api.p.rapidapi.com")
                        .build()
                    chain.proceed(request)
                }.build()
            )
            .build()
            .create(MangaApi::class.java)
    }

}

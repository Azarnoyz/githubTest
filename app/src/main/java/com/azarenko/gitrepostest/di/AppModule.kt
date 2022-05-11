package com.azarenko.gitrepostest.di

import android.content.Context
import androidx.room.Room
import com.azarenko.gitrepostest.BuildConfig
import com.azarenko.gitrepostest.db.ReposDataBase
import com.azarenko.gitrepostest.db.ReposDbRepository
import com.azarenko.gitrepostest.network.ApiService
import com.azarenko.gitrepostest.ui.fragments.repositoriesList.ReposListRepository
import com.azarenko.gitrepostest.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideListRepository(apiService: ApiService): ReposListRepository =
        ReposListRepository(apiService)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ReposDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            ReposDataBase::class.java,
            "ReposDB"
        ).build()

    @Singleton
    @Provides
    fun provideReposDbRepository(appDataBase: ReposDataBase): ReposDbRepository =
        ReposDbRepository(appDataBase)
}
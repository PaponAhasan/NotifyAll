package com.example.notifyall.di

import com.example.notifyall.fcm.CustomInterceptor
import com.example.notifyall.fcm.FcmApi
import com.example.notifyall.repository.NotificationRepository
import com.example.notifyall.repository.NotificationRepositoryImpl
import com.example.notifyall.util.Constants.BASE_URL
import com.example.notifyall.util.Constants.SERVER_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): CustomInterceptor {
        return CustomInterceptor(SERVER_KEY)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(customInterceptor: CustomInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(customInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): FcmApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
            .create(FcmApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteImp(fcmApi: FcmApi): NotificationRepository {
        return NotificationRepositoryImpl(fcmApi)
    }
}
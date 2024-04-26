package com.example.notifyall.fcm

import okhttp3.Interceptor
import javax.inject.Inject
import okhttp3.Response
class CustomInterceptor @Inject constructor(private val serverKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            // Proceed with the request
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "key=$serverKey")
                .addHeader("Content-Type", "application/json")
                .build()
            return chain.proceed(request) // Proceed with the request
        } catch (e: Exception) {
            throw e
        }
    }
}
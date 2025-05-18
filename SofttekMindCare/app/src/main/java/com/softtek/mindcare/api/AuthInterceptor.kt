package com.softtek.mindcare.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer YOUR_API_KEY")
            .build()
        return chain.proceed(request)
    }
}
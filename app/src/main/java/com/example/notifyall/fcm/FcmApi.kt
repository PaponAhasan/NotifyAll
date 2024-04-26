package com.example.notifyall.fcm

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: SendMessageDto
    ) : Response<ResponseBody>
}
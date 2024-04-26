package com.example.notifyall.util

import com.example.notifyall.BuildConfig

object Constants {
    const val USER_COLLECTION = "users"

    const val USER_FIELD_IS_ADMIN = "admin"
    const val SUCCESS_MESSAGE = "Success"
    const val CHANNEL_ID = "channel_id"
    const val CHANNEL_NAME = "notification_Channel"
    const val NOTIFICATION_TOPIC = "/topics/myTopic"
    //const val BASE_URL = "http://10.0.2.2:8080/"
    const val BASE_URL = "https://fcm.googleapis.com/"
    val SERVER_KEY: String = BuildConfig.SERVER_KEY
    const val CONTENT_TYPE = "application/json"
}
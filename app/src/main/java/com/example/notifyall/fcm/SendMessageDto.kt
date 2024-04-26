package com.example.notifyall.fcm

data class SendMessageDto(
    val data: NotificationBody,
    val to: String
)

data class NotificationBody(
    val title: String,
    val message: String
)
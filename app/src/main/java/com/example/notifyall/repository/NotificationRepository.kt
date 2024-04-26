package com.example.notifyall.repository

import com.example.notifyall.fcm.SendMessageDto
import com.example.notifyall.util.Resource
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun sendNotification(sendMessageDto: SendMessageDto) : Flow<Resource<String>>
}
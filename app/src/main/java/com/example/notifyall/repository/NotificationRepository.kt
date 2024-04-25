package com.example.notifyall.repository

import com.example.notifyall.models.Notification
import com.example.notifyall.util.Resource
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun sendNotification(notification: Notification) : Flow<Resource<Unit>>
    fun getNotifications() : Flow<Resource<List<Notification>>>
    fun deleteNotification(id: String) : Flow<Resource<Unit>>
}
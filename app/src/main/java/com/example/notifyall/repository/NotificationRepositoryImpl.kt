package com.example.notifyall.repository

import com.example.notifyall.models.Notification
import com.example.notifyall.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(

): NotificationRepository {
    override fun sendNotification(notification: Notification): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getNotifications(): Flow<Resource<List<Notification>>> {
        TODO("Not yet implemented")
    }

    override fun deleteNotification(id: String): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }
}
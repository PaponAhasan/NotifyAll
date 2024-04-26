package com.example.notifyall.repository

import com.example.notifyall.fcm.FcmApi
import com.example.notifyall.fcm.SendMessageDto
import com.example.notifyall.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val fcmApi: FcmApi
): NotificationRepository {
    override fun sendNotification(sendMessageDto: SendMessageDto): Flow<Resource<String>> {
        return flow<Resource<String>> {
            emit(Resource.Loading())
            fcmApi.postNotification(sendMessageDto)
            emit(Resource.Success("Notification sent"))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}
package dq.status.sticker.saver.domain.repository

import android.content.Context
import dq.status.sticker.saver.data.RepositoryImpl
import dq.status.sticker.saver.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

object Repository {


    suspend fun getStatus(): Flow<Resource> {
        return RepositoryImpl.getStatus()
    }

    suspend fun getImageStatus(context: Context): Flow<Resource> {
        return RepositoryImpl.getStatusImage(context)
    }
    suspend fun getVideoStatus(context: Context): Flow<Resource> {
        return RepositoryImpl.getStatusVideos(context)
    }
}
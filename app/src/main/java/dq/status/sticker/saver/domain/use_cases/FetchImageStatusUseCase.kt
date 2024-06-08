package dq.status.sticker.saver.domain.use_cases

import android.content.Context
import dq.status.sticker.saver.domain.repository.Repository
import dq.status.sticker.saver.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

object FetchImageStatusUseCase {
    suspend fun getImageStatus(context: Context): Flow<Resource> {
        return Repository.getImageStatus(context)
    }
}
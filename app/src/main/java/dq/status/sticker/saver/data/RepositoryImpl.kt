package dq.status.sticker.saver.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import dq.status.sticker.saver.domain.model.StatusItemLocal
import dq.status.sticker.saver.presentation.utils.Constants
import dq.status.sticker.saver.presentation.utils.FileUtils
import dq.status.sticker.saver.presentation.utils.PrefKeys
import dq.status.sticker.saver.presentation.utils.PreferenceUtils
import dq.status.sticker.saver.presentation.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object RepositoryImpl {
    suspend fun getStatusImage(context: Context): Flow<Resource> {
        val activity = context as Activity
        return flow {
            emit(Resource.Loading)
            delay(1000)
            try {
                val treeUri =
                    PreferenceUtils.getPrefString(PrefKeys.PREF_KEY_WP_TREE_URI, "")?.toUri()!!
                activity?.contentResolver?.takePersistableUriPermission(
                    treeUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val fileDocument = DocumentFile.fromTreeUri(activity, treeUri)
                val list = arrayListOf<StatusItemLocal>()
                fileDocument?.let {
                    it.listFiles().forEach { file ->
                        if (file.name != ".nomedia" && file.isFile) {
                            if (!FileUtils.getFileExtension(file?.name.toString())
                                    .contains(".mp4")
                            ) {
                                val item = StatusItemLocal(
                                    uri = file.uri.toString(),
                                    fileName = file.name.toString(),
                                    type = Constants.MEDIA_TYPE_IMAGE
                                )
                                list.add(item)
                            }
                        }
                    }
                }
                emit(Resource.Success(list))
            } catch (ex: Exception) {
                emit(Resource.Error(ex.localizedMessage))
            }
        }
    }
    suspend fun getStatusVideos(context: Context): Flow<Resource> {
        val activity = context as Activity
        return flow {
            emit(Resource.Loading)
            delay(1000)
            try {
                val treeUri =
                    PreferenceUtils.getPrefString(PrefKeys.PREF_KEY_WP_TREE_URI, "")?.toUri()!!
                activity?.contentResolver?.takePersistableUriPermission(
                    treeUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val fileDocument = DocumentFile.fromTreeUri(activity, treeUri)
                val list = arrayListOf<StatusItemLocal>()
                fileDocument?.let {
                    it.listFiles().forEach { file ->
                        if (file.name != ".nomedia" && file.isFile) {
                            if (FileUtils.getFileExtension(file?.name.toString())
                                    .contains(".mp4")
                            ) {
                                val item = StatusItemLocal(
                                    uri = file.uri.toString(),
                                    fileName = file.name.toString(),
                                    type = Constants.MEDIA_TYPE_VIDEO
                                )
                                list.add(item)
                            }
                        }
                    }
                }
                emit(Resource.Success(list))
            } catch (ex: Exception) {
                emit(Resource.Error(ex.localizedMessage))
            }
        }
    }

    suspend fun getStatus(): Flow<Resource> {
        return flow {
            emit(
                Resource
                    .Loading
            )

            try {
                val data = "Hello"
                delay(2000)
                emit(Resource.Success(listOf(data)))
            } catch (ex: Exception) {
                emit(Resource.Error(ex.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }
}
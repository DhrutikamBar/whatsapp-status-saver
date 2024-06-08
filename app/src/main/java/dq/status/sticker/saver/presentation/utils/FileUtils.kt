package dq.status.sticker.saver.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import dq.status.sticker.saver.R
import dq.status.sticker.saver.domain.model.StatusItemLocal
import java.io.File

object FileUtils {

    // get if a status exist using Kotlin extension function
    fun isStatusExist(fileName: String, context: Context): Boolean {
        val dirName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File("${dirName}/${context.getString(R.string.app_name)}", fileName)
        return file.exists()
    }


    fun getFileExtension(fName: String): String {
        val lastDotInd = fName.lastIndexOf(".")
        if (lastDotInd > 0 && lastDotInd < fName.length - 1) {
            return fName.substring(lastDotInd - 1)
        }
        return ""
    }

    fun saveStatus(item: StatusItemLocal, context: Context): Boolean {
        if (isStatusExist(item.fileName, context)) {
            return true
        }
        val extension = getFileExtension(item.fileName)
        val mimeType = "${item.type}/$extension"
        val inputStream = context.contentResolver.openInputStream(item.uri.toUri())
        try {
            val values = ContentValues()
            values.apply {
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.DISPLAY_NAME, item.fileName)
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "${Environment.DIRECTORY_DOCUMENTS}/${context.getString(R.string.app_name)}"
                )
            }
            val uri =
                context.contentResolver.insert(MediaStore.Files.getContentUri("external"), values)
            uri?.let {
                val outputStream = context.contentResolver.openOutputStream(it)
                if (inputStream != null) {
                    outputStream?.write(inputStream.readBytes())
                }
                outputStream?.close()
                inputStream?.close()
            }
            return true
        } catch (ex: Exception) {
            Log.e("SAVE_STATUS_EXCEPTION", ex.localizedMessage)
            return false
        }
    }
}
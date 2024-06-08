package dq.status.sticker.saver.presentation.view_models

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dq.status.sticker.saver.presentation.utils.Constants
import java.io.File

class ViewStatusViewModel() : ViewModel() {


    val isStatusDownloaded = MutableLiveData<Boolean>(false)

    fun repostStatus(
        file: File,
        statusType: String,
        context: Context
    ) {


        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND)
        if (statusType == Constants.MEDIA_TYPE_IMAGE) {
            intent.type = "image/*"
        } else {
            intent.type = "video/*"
        }
        intent.setPackage("com.whatsapp")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(intent)


    }

    fun shareStatus(
        file: File,
        statusType: String,
        context: Context
    ) {


        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND)
        if (statusType == Constants.MEDIA_TYPE_IMAGE) {
            intent.type = "image/*"
        } else {
            intent.type = "video/*"
        }

        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(intent, "Share file using"))


    }


}
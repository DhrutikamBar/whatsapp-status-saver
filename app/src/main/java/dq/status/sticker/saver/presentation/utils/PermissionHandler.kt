package dq.status.sticker.saver.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.core.app.ActivityCompat.startActivityForResult


fun getFolderPermissionForWhatsapp(context: Context, REQ_CODE: Int, initialUri: Uri) {
    val activity = context as Activity
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
        putExtra("android.content.extra.SHOW_ADVANCED", true)
    }
    startActivityForResult(activity,intent, REQ_CODE,null)
}

package dq.status.sticker.saver.presentation.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat

object Utils {

    fun isAllPermissionGranted(context: Context?, permissions: Array<String>): Boolean {
        var isAllGranted = false
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("isAllPermissionGranted-->", isAllGranted.toString())
                isAllGranted = true
            }
        }
        return isAllGranted
    }
}

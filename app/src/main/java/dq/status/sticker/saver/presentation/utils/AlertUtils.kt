package dq.status.sticker.saver.presentation.utils

import android.content.Context
import android.widget.Toast

class AlertUtils {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
package dq.status.sticker.saver

import android.app.Application
import dq.status.sticker.saver.presentation.utils.PreferenceUtils

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceUtils.init(this)
    }
}
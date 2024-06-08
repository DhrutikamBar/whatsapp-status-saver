package dq.status.sticker.saver.presentation.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dq.status.sticker.saver.R
import dq.status.sticker.saver.presentation.utils.PrefKeys
import dq.status.sticker.saver.presentation.utils.PreferenceUtils
import dq.status.sticker.saver.presentation.utils.Utils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val requiredPermission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES
    )

    private val REQUEST_CODE = 10001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val isStatusPermissionGranted =
            PreferenceUtils.getPrefBoolean(PrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)
        if (!Utils.isAllPermissionGranted(this, requiredPermission)) {
            startActivity(Intent(this, RequestPermissionActivity::class.java))
            finish()
        }else if (!isStatusPermissionGranted){
            startActivity(Intent(this, RequestPermissionActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}
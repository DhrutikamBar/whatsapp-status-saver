package dq.status.sticker.saver.presentation.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dq.status.sticker.saver.databinding.ActivityRequestPermissionBinding
import dq.status.sticker.saver.presentation.utils.AlertUtils
import dq.status.sticker.saver.presentation.utils.Constants
import dq.status.sticker.saver.presentation.utils.PrefKeys
import dq.status.sticker.saver.presentation.utils.PreferenceUtils
import dq.status.sticker.saver.presentation.utils.Utils
import dq.status.sticker.saver.presentation.utils.getFolderPermissionForWhatsapp


class RequestPermissionActivity : AppCompatActivity() {
    private val requiredPermission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES

    )
    private var REQ_CODE_WHATSAPP = 101
    private var REQ_CODE_WHATSAPP_BUSINESS = 102

    private lateinit var binding: ActivityRequestPermissionBinding

    private val REQUEST_CODE = 10001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                // Do something if permission granted
                if (isGranted) {
                    updateUI()
                } else {
                    AlertUtils.showToast(this, "Permission Denied!")
                }
            }

        updateUI()
        binding.btnAllowPermission.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(
                    Manifest.permission.READ_MEDIA_IMAGES,
                )
            } else {
                launcher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                )
            }

        }
        binding.btnAllowStatusFolder.setOnClickListener {
            getFolderPermissionForWhatsapp(this, REQ_CODE_WHATSAPP, Constants.getWhatsappUri())
        }
    }

    private fun updateUI() {
        val isStatusPermissionGranted =
            PreferenceUtils.getPrefBoolean(PrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)
        val isDeviceStorageGranted = Utils.isAllPermissionGranted(this, requiredPermission)
        if (!isDeviceStorageGranted) {
            binding.llDeviceStorage.visibility = View.VISIBLE
            binding.llStatusTree.visibility = View.GONE
        } else if (!isStatusPermissionGranted) {
            binding.llDeviceStorage.visibility = View.GONE
            binding.llStatusTree.visibility = View.VISIBLE
        } else {
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Toast.makeText(requireActivity(), "Hello", Toast.LENGTH_LONG).show()
        if (resultCode == Activity.RESULT_OK) {
            val treeUri = data?.data!!
            // save persistable permission
            contentResolver.takePersistableUriPermission(
                treeUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (requestCode == REQ_CODE_WHATSAPP) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                // save whatsapp tree uri
                PreferenceUtils.putPrefString(PrefKeys.PREF_KEY_WP_TREE_URI, treeUri.toString())
                PreferenceUtils.putPrefBoolean(PrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, true)
                goToMainActivity()
            }
            if (resultCode == REQ_CODE_WHATSAPP_BUSINESS) {
                // save whatsapp business tree uri
            }
        }
    }


    /*
        @Deprecated("Deprecated in Java")
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == REQUEST_CODE) {
                if (!Utils.isAllPermissionGranted(this, requiredPermission)
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        requiredPermission,
                        REQUEST_CODE
                    )
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }*/
}
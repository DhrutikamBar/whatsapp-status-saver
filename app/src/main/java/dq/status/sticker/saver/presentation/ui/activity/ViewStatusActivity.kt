package dq.status.sticker.saver.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.MediaController
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dq.status.sticker.saver.R
import dq.status.sticker.saver.databinding.ActivityViewStatusBinding
import dq.status.sticker.saver.domain.model.StatusItemLocal
import dq.status.sticker.saver.presentation.utils.AlertUtils
import dq.status.sticker.saver.presentation.utils.Constants
import dq.status.sticker.saver.presentation.utils.FileUtils
import dq.status.sticker.saver.presentation.view_models.ViewStatusViewModel
import java.io.File

class ViewStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewStatusBinding
    private lateinit var viewModel: ViewStatusViewModel
    private var statusType = ""
    private var statusUrl = ""
    private var fileName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewStatusBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ViewStatusViewModel::class.java]
        setContentView(binding.root)

        onClicks()
        if (intent != null) {
            if (intent.hasExtra("statusType")) {
                statusType = intent.getStringExtra("statusType").toString()
            }
            if (intent.hasExtra("fileName")) {
                fileName = intent.getStringExtra("fileName").toString()
            }

            if (intent.hasExtra("url")) {
                statusUrl = intent.getStringExtra("url").toString()
            }
        }

        if (statusType == Constants.MEDIA_TYPE_IMAGE) {
            loadImageView()
        }

        if (statusType == Constants.MEDIA_TYPE_VIDEO) {
            loadVideoView()
        }

        if (FileUtils.isStatusExist(fileName, this)) {
            viewModel.isStatusDownloaded.value = true
            observeFileDownloadStatus()
        }

    }

    private fun observeFileDownloadStatus() {
        viewModel.isStatusDownloaded.observe(this) {
            if (it) {
                binding.ivSaveStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_file_download_done_24
                    )
                )
                binding.tvSaveStatus.setText("Saved")
            } else {
                binding.ivSaveStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_save_24
                    )
                )
                binding.tvSaveStatus.setText("Save")
            }
        }
    }

    private fun onClicks() {
        binding.backIcon.setOnClickListener {
            finish()
        }

        binding.llSaveStatus.setOnClickListener {
            if (!FileUtils.isStatusExist(fileName, this)) {
                val item = StatusItemLocal(
                    uri = statusUrl,
                    fileName = fileName,
                    type = Constants.MEDIA_TYPE_IMAGE
                )
                FileUtils.saveStatus(item, this)
                viewModel.isStatusDownloaded.value = true
                observeFileDownloadStatus()
            } else {
                AlertUtils.showToast(this, "Status saved")
            }

        }

        binding.llRepostStatus.setOnClickListener {
            repostStatus()
        }

        binding.llShareStatus.setOnClickListener {
            shareStatus()
        }
    }

    private fun repostStatus() {
        val dirName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File("${dirName}/${this.getString(R.string.app_name)}", fileName)
        if (file.exists()) {
            viewModel.repostStatus(file, statusType, this)
        } else {
            val item = StatusItemLocal(
                uri = statusUrl,
                fileName = fileName,
                type = Constants.MEDIA_TYPE_IMAGE
            )
            if (FileUtils.saveStatus(item, this)) {
                if (file.exists()) {
                    viewModel.repostStatus(file, statusType, this)
                }
            }
        }
    }

    private fun shareStatus() {
        val dirName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File("${dirName}/${this.getString(R.string.app_name)}", fileName)
        if (file.exists()) {
            viewModel.shareStatus(file, statusType, this)
        } else {
            val item = StatusItemLocal(
                uri = statusUrl,
                fileName = fileName,
                type = Constants.MEDIA_TYPE_IMAGE
            )
            if (FileUtils.saveStatus(item, this)) {
                if (file.exists()) {
                    viewModel.shareStatus(file, statusType, this)
                }
            }
        }
    }

    private fun loadImageView() {
        binding.ivStatusImageView.visibility = View.VISIBLE
        binding.llVideoView.visibility = View.GONE
        Glide.with(this).load(statusUrl.toUri()).into(binding.ivStatusImageView)
    }

    private fun loadVideoView() {
        binding.ivStatusImageView.visibility = View.GONE
        binding.llVideoView.visibility = View.VISIBLE

        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoURI(statusUrl.toUri())
        binding.videoView.start()
    }

    override fun onResume() {
        super.onResume()
        if (FileUtils.isStatusExist(fileName, this)) {
            viewModel.isStatusDownloaded.value = true
            observeFileDownloadStatus()
        }
    }
}
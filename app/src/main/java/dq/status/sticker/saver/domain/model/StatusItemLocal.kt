package dq.status.sticker.saver.domain.model

import dq.status.sticker.saver.presentation.utils.Constants


data class StatusItemLocal(
    val uri: String,
    val fileName: String,
    val type: String? = Constants.MEDIA_TYPE_IMAGE,
    val isDownloaded: Boolean? = false
) {
}
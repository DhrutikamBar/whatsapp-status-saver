package dq.status.sticker.saver.presentation.utils


sealed class Resource {
    object Loading : Resource()
    data class Success<T>(val data: T) : Resource()
    data class Error(val message: String) : Resource()
}
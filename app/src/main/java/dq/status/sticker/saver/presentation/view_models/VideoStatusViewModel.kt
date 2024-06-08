package dq.status.sticker.saver.presentation.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dq.status.sticker.saver.domain.use_cases.FetchVideoStatusUseCase
import dq.status.sticker.saver.presentation.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class VideoStatusViewModel : ViewModel() {
    private val videoStatusList = MutableStateFlow<Resource>(Resource.Loading)

    fun getVideoStatus(context: Context): MutableStateFlow<Resource> {
        viewModelScope.launch {
            FetchVideoStatusUseCase.getVideoStatus(context).collect {
                videoStatusList.value = it
            }
        }
        return videoStatusList
    }
}
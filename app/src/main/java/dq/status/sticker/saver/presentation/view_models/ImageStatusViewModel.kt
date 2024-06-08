package dq.status.sticker.saver.presentation.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dq.status.sticker.saver.domain.use_cases.FetchImageStatusUseCase
import dq.status.sticker.saver.presentation.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ImageStatusViewModel : ViewModel() {
    private val imageStatusList = MutableStateFlow<Resource>(Resource.Loading)

    fun getImageStatus(context: Context): MutableStateFlow<Resource> {
        viewModelScope.launch {
            FetchImageStatusUseCase.getImageStatus(context).collect {
                imageStatusList.value = it
            }
        }
        return imageStatusList
    }

}
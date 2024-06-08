package dq.status.sticker.saver.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dq.status.sticker.saver.domain.use_cases.FetchVideoStatusUseCase
import dq.status.sticker.saver.presentation.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    val status = MutableStateFlow<Resource>(Resource.Loading)

    init {
        getStatus()
    }
    private fun getStatus() {
        viewModelScope.launch {
            FetchVideoStatusUseCase.getStatus().collect {
                status.value = it
            }
        }
    }
}
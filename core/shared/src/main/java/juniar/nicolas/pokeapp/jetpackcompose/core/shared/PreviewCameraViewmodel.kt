package juniar.nicolas.pokeapp.jetpackcompose.core.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreviewCameraViewmodel : ViewModel() {
    private val _photoUri = MutableStateFlow("")
    val photoUri = _photoUri.asStateFlow()

    fun savePhotoUri(uri: String) {
        _photoUri.value = uri
    }

    fun clear() {
        _photoUri.value = ""
    }
}
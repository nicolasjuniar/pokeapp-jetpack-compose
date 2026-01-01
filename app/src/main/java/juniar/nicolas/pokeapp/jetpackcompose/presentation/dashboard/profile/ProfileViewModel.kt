package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ResultWrapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.ChangePasswordUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.UpdateUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.DefaultSignal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserProfilePictureUseCase: GetUserProfilePictureUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ProfileState, ProfileEvent, DefaultSignal>(ProfileState()) {


    init {
        viewModelScope.launch {
            val username = getLoggedUsernameUseCase().first()
            if (username.isNotEmpty()) {
                val uri = getUserProfilePictureUseCase.invoke(username)
                setState { copy(imageUri = uri, loggedUsername = username) }
            }
        }
    }

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ChangePictureClicked -> {
                setState { copy(activeSheet = ProfileSheet.ChangeProfilePicture) }
            }

            is ProfileEvent.ChangePasswordClicked -> {
                setState { copy(activeSheet = ProfileSheet.ChangePassword) }
            }

            is ProfileEvent.DismissBottomSheet -> {
                setState { copy(activeSheet = null) }
            }

            is ProfileEvent.UpdateImageUri -> {
                viewModelScope.launch {
                    val uri = event.imageUri
                    setState {
                        copy(imageUri = uri)
                    }
                    if (state.value.loggedUsername.isEmpty()) return@launch
                    updateUserProfilePictureUseCase.invoke(uri, state.value.loggedUsername)
                    sendSignal(DefaultSignal.ShowToast("Success Update Profile Picture"))
                }
            }
        }
    }
}

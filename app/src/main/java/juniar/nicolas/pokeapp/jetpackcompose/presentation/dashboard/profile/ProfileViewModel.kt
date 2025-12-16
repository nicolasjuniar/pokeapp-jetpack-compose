package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.domain.usecase.UpdateUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserProfilePictureUseCase: GetUserProfilePictureUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase
) : BaseViewModel<ProfileState, ProfileEvent, ProfileSignal>(ProfileState()) {


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
                setState { copy(showChangeProfilePictureBottomSheet = true) }
            }

            is ProfileEvent.ChangePasswordClicked -> {
                viewModelScope.launch {
                    sendSignal(ProfileSignal.showChangePasswordBottomSheet)
                }
            }

            is ProfileEvent.DismissChangeProfilePictureBottomSheet -> {
                setState { copy(showChangeProfilePictureBottomSheet = false) }
            }

            is ProfileEvent.UpdateImageUri -> {
                viewModelScope.launch {
                    val uri = event.imageUri
                    setState {
                        copy(imageUri = uri)
                    }
                    if (state.value.loggedUsername.isEmpty()) return@launch
                    updateUserProfilePictureUseCase.invoke(uri, state.value.loggedUsername)
                    sendSignal(ProfileSignal.successUpdateProfilePicture)
                }
            }
        }
    }
}

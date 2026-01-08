package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.BaseViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetLoggedUsernameUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.GetUserProfilePictureUseCase
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.domain.UpdateUserProfilePictureUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserProfilePictureUseCase: GetUserProfilePictureUseCase,
    getLoggedUsernameUseCase: GetLoggedUsernameUseCase,
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase
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

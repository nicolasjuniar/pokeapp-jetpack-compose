package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.PreviewCameraViewmodel
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile.changepassword.ChangePasswordBottomSheet
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile.changepassword.ChangePasswordSignal
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile.changepassword.ChangePasswordViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    changePasswordViewmodel: ChangePasswordViewmodel = hiltViewModel(),
    previewCameraViewmodel: PreviewCameraViewmodel,
    openCameraScreen: () -> Unit = {}
) {
    val context = LocalContext.current

    val profileState by profileViewModel.state.collectAsStateWithLifecycle()

    val changePasswordState by changePasswordViewmodel.state.collectAsStateWithLifecycle()

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            profileViewModel.onEvent(ProfileEvent.UpdateImageUri(it.toString()))
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        profileViewModel.signal.collect {
            if (it is DefaultSignal.ShowToast) {
                context.showToast(it.message)
            }
        }
    }

    LaunchedEffect(Unit) {
        changePasswordViewmodel.signal.collect {
            when (it) {
                is ChangePasswordSignal.ShowToast -> {
                    context.showToast(it.message)
                }

                is ChangePasswordSignal.DismissChangePasswordBottomSheet -> {
                    profileViewModel.onEvent(ProfileEvent.DismissBottomSheet)
                }
            }
        }
    }

    val photoUri by previewCameraViewmodel.photoUri.collectAsStateWithLifecycle()

    LaunchedEffect(photoUri) {
        if (photoUri.isNotEmpty()) {
            profileViewModel.onEvent(
                ProfileEvent.UpdateImageUri(photoUri)
            )
            previewCameraViewmodel.clear()
        }
    }


    when (profileState.activeSheet) {
        is ProfileSheet.ChangeProfilePicture -> {
            ChangeProfilePictureBottomSheet(
                sheetState = sheetState,
                dismissBottomSheet = {
                    profileViewModel.onEvent(ProfileEvent.DismissBottomSheet)
                },
                chooseGallery = {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                takePicture = {
                    scope.launch {
                        sheetState.hide()
                        profileViewModel.onEvent(ProfileEvent.DismissBottomSheet)
                        openCameraScreen()
                    }
                }
            )
        }

        is ProfileSheet.ChangePassword -> {
            ChangePasswordBottomSheet(
                sheetState = sheetState,
                state = changePasswordState,
                onEvent = changePasswordViewmodel::onEvent,
                onDismiss = {
                    profileViewModel.onEvent(ProfileEvent.DismissBottomSheet)
                }
            )
        }

        else -> Unit
    }
    ProfileContent(
        profileState = profileState,
        onEvent = profileViewModel::onEvent
    )
}

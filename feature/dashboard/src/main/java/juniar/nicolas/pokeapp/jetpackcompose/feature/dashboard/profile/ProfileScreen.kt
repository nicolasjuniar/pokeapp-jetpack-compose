package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import juniar.nicolas.pokeapp.jetpackcompose.core.common.DefaultSignal
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.PreviewCameraViewmodel
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.component.LoadingOverlay
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile.changepassword.ChangePasswordBottomSheet
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile.changepassword.ChangePasswordEvent
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile.changepassword.ChangePasswordSignal
import juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.profile.changepassword.ChangePasswordViewmodel
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
                oldPassword = changePasswordState.oldPassword,
                oldPasswordOnChange = {
                    changePasswordViewmodel.onEvent(ChangePasswordEvent.OldPasswordChanged(it))
                },
                newPassword = changePasswordState.newPassword,
                newPasswordOnChange = {
                    changePasswordViewmodel.onEvent(ChangePasswordEvent.NewPasswordChanged(it))
                },
                confirmPassword = changePasswordState.confirmPassword,
                confirmPasswordOnChange = {
                    changePasswordViewmodel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged(it))
                },
                onDismiss = {
                    changePasswordViewmodel.onEvent(ChangePasswordEvent.ResetChangePasswordField)
                    profileViewModel.onEvent(ProfileEvent.DismissBottomSheet)
                },
                changePasswordEnabled = changePasswordState.changePasswordButtonEnabled,
                changePasswordOnClick = {
                    changePasswordViewmodel.onEvent(ChangePasswordEvent.ChangePasswordButtonClicked)
                }
            )
        }

        else -> Unit
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { profileViewModel.onEvent(ProfileEvent.ChangePictureClicked) },
            contentAlignment = Alignment.Center
        ) {
            if (profileState.imageUri.isNotEmpty()) {
                AsyncImage(
                    model = profileState.imageUri,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0DCEB)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(70.dp),
                        tint = Color(0xFF4B4453)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = Color.Black
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = { profileViewModel.onEvent(ProfileEvent.ChangePasswordClicked) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password")
        }
    }
    if (profileState.isLoading) {
        LoadingOverlay()
    }
}

package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfilePictureBottomSheet(
    sheetState: SheetState,
    dismissBottomSheet: () -> Unit,
    chooseGallery: () -> Unit,
    takePicture: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { dismissBottomSheet() }) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Change Profile Picture",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            SheetOption(
                label = "Choose from Gallery", icon = Icons.Outlined.Image, onClick = {
                    dismissBottomSheet()
                    chooseGallery()
                })

            SheetOption(
                label = "Take a Picture", icon = Icons.Outlined.CameraAlt, onClick = {
                    dismissBottomSheet()
                    takePicture()
                })

            Spacer(Modifier.height(20.dp))
        }
    }
}

package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen

@Composable
fun PreviewScreen(
    imageUri: String,
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Fullscreen Image
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Bottom Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Retake
            IconButton(
                onClick = {
                    navController.popBackStack()   // balik ke camera
                },
                modifier = Modifier
                    .size(68.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Retake",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            // OK
            IconButton(
                onClick = {
                    // kirim hasil dulu (ke siapa pun yang butuh)
                    navController.getBackStackEntry(Screen.Dashboard.route)
                        .savedStateHandle["capturedImage"] = imageUri

                    // hapus Camera dari backstack + Preview ikut hilang
                    navController.popBackStack(
                        route = Screen.Camera.route,
                        inclusive = true
                    )

                },
                modifier = Modifier
                    .size(68.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "OK",
                    tint = Color.Green,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }
}



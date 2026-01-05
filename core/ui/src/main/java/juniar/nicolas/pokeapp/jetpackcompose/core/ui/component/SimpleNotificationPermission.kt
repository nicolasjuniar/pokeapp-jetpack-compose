package juniar.nicolas.pokeapp.jetpackcompose.core.ui.component

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast

@Composable
fun SimpleNotificationPermission() {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        val message = if (granted) {
            "Notifications enabled. You can now view API logs via Chucker."
        } else {
            "Enable notifications if you want to view API logs via Chucker."
        }

        context.showToast(message)
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val isGranted = ContextCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                launcher.launch(permission)
            }
        }
    }
}

package juniar.nicolas.pokeapp.jetpackcompose.feature.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast

@Composable
fun CameraScreen(
    onBackClick: () -> Unit = {},
    openPreviewScreen: (encodedUri: String) -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val lifecycleOwner = LocalLifecycleOwner.current

    var cameraState by remember { mutableStateOf<CameraState>(CameraState.NeedPermission) }

    val permissionLauncher = createPermissionLauncher(
        permission = Manifest.permission.CAMERA,
        activity = activity,
        context = context,
        onGranted = {
            cameraState = CameraState.ShowCamera
        },
        onDenied = {
            cameraState = CameraState.NeedPermission
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (hasCameraPermission(context)) {
                    cameraState = CameraState.ShowCamera
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (cameraState) {
        CameraState.NeedPermission -> {
            DefaultScreen(onRequestPermission = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            })
        }

        CameraState.ShowCamera -> {
            CameraContent(
                onBack = onBackClick,
                onPhotoTaken = { uri ->
                    val encodedUri = Uri.encode(uri.toString())
                    openPreviewScreen(encodedUri)
                }
            )
        }
    }
}

sealed interface CameraState {
    data object NeedPermission : CameraState
    data object ShowCamera : CameraState
}

@Composable
fun DefaultScreen(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Camera permission is required to take photos",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRequestPermission) {
            Text("Allow Camera Permission")
        }
    }
}

fun Context.findActivity(): Activity =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> error("Activity not found")
    }

@Composable
fun createPermissionLauncher(
    permission: String,
    activity: Activity,
    context: Context,
    onGranted: () -> Unit,
    onDenied: () -> Unit,
): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        val permissionStatus = ContextCompat.checkSelfPermission(activity, permission)
        val shouldShowRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            permission
        )
        when {
            granted -> onGranted()
            permissionStatus != PackageManager.PERMISSION_GRANTED && !shouldShowRequestPermission -> {
                context.showToast("Camera access requires manual permission")
                openAppSettings(
                    context
                )
            }

            else -> {
                onDenied()
            }
        }
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}

fun hasCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

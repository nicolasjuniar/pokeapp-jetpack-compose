package juniar.nicolas.pokeapp.jetpackcompose.core.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.security.MessageDigest

fun Context.showToast(message: String, isLong: Boolean = false) {
    Toast.makeText(
        this,
        message,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}

fun String.hash(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun NavController.navigateScreen(
    to: String,
    popUpTo: String? = null,
    inclusive: Boolean = false,
) {
    this.navigate(to) {
        if (popUpTo != null) {
            popUpTo(popUpTo) {
                this.inclusive = inclusive
            }
        }
    }
}

fun Int?.orEmpty(defaultValue: Int = -1) = this ?: defaultValue

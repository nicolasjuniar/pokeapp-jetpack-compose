package juniar.nicolas.pokeapp.jetpackcompose.core

import android.content.Context
import android.widget.Toast
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

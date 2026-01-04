package juniar.nicolas.pokeapp.jetpackcompose.core.common

import android.content.Context
import android.widget.Toast
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
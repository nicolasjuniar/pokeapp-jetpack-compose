package juniar.nicolas.pokeapp.jetpackcompose.core.common

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, isLong: Boolean = false) {
    Toast.makeText(
        this,
        message,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}
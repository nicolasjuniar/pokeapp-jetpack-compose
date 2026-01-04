package juniar.nicolas.pokeapp.jetpackcompose.core

import androidx.navigation.NavController

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

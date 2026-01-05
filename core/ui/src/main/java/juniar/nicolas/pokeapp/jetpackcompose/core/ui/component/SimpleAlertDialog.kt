package juniar.nicolas.pokeapp.jetpackcompose.core.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SimpleDialog(
    title: String,
    description: String,
    confirmText: String = "Yes",
    dismissText: String = "No",
    showOnChange: (Boolean) -> Unit,
    confirmOnClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showOnChange(false) },
        title = { Text(title) },
        text = { Text(description) },
        confirmButton = {
            TextButton(
                onClick = {
                    showOnChange(false)
                    confirmOnClick()
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { showOnChange(false) }
            ) {
                Text(dismissText)
            }
        }
    )
}
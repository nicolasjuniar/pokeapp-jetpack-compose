package juniar.nicolas.pokeapp.jetpackcompose.presentation.dashboard.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.PasswordTextField
import juniar.nicolas.pokeapp.jetpackcompose.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordBottomSheet(
    oldPassword: String,
    oldPasswordOnChange: (String) -> Unit,
    newPassword: String,
    newPasswordOnChange: (String) -> Unit,
    confirmPassword: String,
    confirmPasswordOnChange: (String) -> Unit,
    onDismiss: () -> Unit,
    changePasswordEnabled: Boolean,
    changePasswordOnClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Change Password",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordTextField(
                value = oldPassword,
                onValueChange = {
                    oldPasswordOnChange(it)
                },
                label = "Old Password",
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = newPassword,
                onValueChange = {
                    newPasswordOnChange(it)
                },
                isError = newPassword.length in 1..6,
                errorText = "Password minimum 6 character",
                label = "New Password",
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPasswordOnChange(it)
                },
                isError = newPassword != confirmPassword,
                errorText = "Confirm Password and New Password must be same",
                label = "Confirm Password"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { onDismiss() },
                    border = BorderStroke(1.dp, Purple40),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Purple40
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(4.dp))

                Button(
                    onClick = { changePasswordOnClick() },
                    enabled = changePasswordEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

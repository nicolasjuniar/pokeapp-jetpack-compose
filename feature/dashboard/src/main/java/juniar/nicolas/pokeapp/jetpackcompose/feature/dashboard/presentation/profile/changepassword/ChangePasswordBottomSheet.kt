package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard.presentation.profile.changepassword

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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.component.PasswordTextField
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordBottomSheet(
    sheetState: SheetState,
    state: ChangePasswordState,
    onEvent: (ChangePasswordEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
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
                value = state.oldPassword,
                onValueChange = {
                    onEvent(ChangePasswordEvent.OldPasswordChanged(it))
                },
                label = "Old Password",
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = state.newPassword,
                onValueChange = {
                    onEvent(ChangePasswordEvent.NewPasswordChanged(it))
                },
                isError = state.newPassword.length in 1..6,
                errorText = "Password minimum 6 character",
                label = "New Password",
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = state.confirmPassword,
                onValueChange = {
                    onEvent(ChangePasswordEvent.ConfirmPasswordChanged(it))
                },
                isError = state.newPassword != state.confirmPassword,
                errorText = "Confirm Password and New Password must be same",
                label = "Confirm Password"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        onEvent(ChangePasswordEvent.ResetChangePasswordField)
                        onDismiss()
                    },
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
                    onClick = { onEvent(ChangePasswordEvent.ChangePasswordButtonClicked) },
                    enabled = state.changePasswordButtonEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

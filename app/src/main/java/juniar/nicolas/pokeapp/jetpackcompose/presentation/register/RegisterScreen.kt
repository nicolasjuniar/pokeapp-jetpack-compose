package juniar.nicolas.pokeapp.jetpackcompose.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.navigateScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.common.BaseScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.PasswordTextField
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val username = viewModel.username
    val password = viewModel.password
    val confirmPassword = viewModel.confirmPassword
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.isRegisterSuccess.collectLatest {
            if (it) {
                navController.navigateScreen(
                    Screen.Main.route,
                    Screen.Register.route,
                    true
                )
            }
        }
    }

    fun openLoginScreen() {
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Register.route) { inclusive = true }
        }
    }

    BaseScreen(viewModel) {

        Surface(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Create account",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        viewModel.onChangeUsername(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = password,
                    onValueChange = {
                        viewModel.onChangePassword(it)
                    },
                    label = "Password",
                    isError = password.length in 1..6,
                    errorText = "Password minimum 6 character",
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = confirmPassword,
                    onValueChange = {
                        viewModel.onChangeConfirmPassword(it)
                    },
                    isError = confirmPassword.isNotEmpty() && confirmPassword != password,
                    errorText = "Confirm Password and Password must same",
                    label = "Confirm Password"
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.register(username.trim(), password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = viewModel.isButtonEnabled.value
                ) {
                    Text(text = "Register")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    openLoginScreen()
                }) {
                    Text(text = "Already have an account? Login")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(navController = rememberNavController())
    }
}
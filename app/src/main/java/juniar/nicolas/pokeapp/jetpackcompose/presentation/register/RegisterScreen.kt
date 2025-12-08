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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.core.navigateScreen
import juniar.nicolas.pokeapp.jetpackcompose.core.showToast
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.LoadingOverlay
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.PasswordTextField
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    fun openMainScreen() {
        navController.navigateScreen(
            Screen.Dashboard.route,
            Screen.Register.route,
            true
        )
    }

    fun openLoginScreen() {
        navController.navigateScreen(
            Screen.Login.route,
            Screen.Register.route,
            true
        )
    }

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            when (it) {
                is RegisterSignal.ShowToast -> context.showToast(it.message)
                is RegisterSignal.NavigateToMain -> openMainScreen()
                is RegisterSignal.NavigateToLogin -> openLoginScreen()
            }
        }
    }

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
                value = state.username,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.UsernameChanged(it))
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
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.PasswordChanged(it))
                },
                label = "Password",
                isError = state.password.length in 1..6,
                errorText = "Password minimum 6 character",
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = state.confirmPassword,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(it))
                },
                isError = state.confirmPassword.isNotEmpty() && state.confirmPassword != state.password,
                errorText = "Confirm Password and Password must same",
                label = "Confirm Password"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onEvent(RegisterEvent.RegisterButtonClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = state.isButtonEnabled
            ) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                viewModel.onEvent(RegisterEvent.LoginTextClicked)
            }) {
                Text(text = "Already have an account? Login")
            }
        }
    }
    if (state.isLoading) {
        LoadingOverlay()
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(navController = rememberNavController())
    }
}

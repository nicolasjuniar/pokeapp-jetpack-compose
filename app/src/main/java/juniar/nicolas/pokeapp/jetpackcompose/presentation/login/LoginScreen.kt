package juniar.nicolas.pokeapp.jetpackcompose.presentation.login

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
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    fun openRegisterScreen() {
        navController.navigateScreen(
            Screen.Register.route,
            Screen.Login.route,
            true
        )
    }

    fun openMainScreen() {
        navController.navigateScreen(
            Screen.Main.route,
            Screen.Login.route,
            true
        )
    }

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            when (it) {
                is LoginSignal.ShowToast ->
                    context.showToast(it.message)

                LoginSignal.NavigateToMain -> openMainScreen()

                LoginSignal.NavigateToRegister -> openRegisterScreen()
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
                text = "Welcome Back",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.username,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.UsernameChanged(it))
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
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                },
                label = "Password"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onEvent(LoginEvent.LoginButtonClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = state.isButtonEnabled
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                viewModel.onEvent(LoginEvent.RegisterTextClicked)
            }) {
                Text(text = "Don't have an account? Register")
            }
        }
    }

    if (state.isLoading) {
        LoadingOverlay()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(navController = rememberNavController())
    }
}

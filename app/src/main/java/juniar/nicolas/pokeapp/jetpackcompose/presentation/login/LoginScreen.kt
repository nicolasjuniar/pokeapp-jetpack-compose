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

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val username = viewModel.username
    val password = viewModel.password
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.isLoginSuccess.collect {
            if (it) {
                navController.navigateScreen(
                    Screen.Main.route,
                    Screen.Login.route,
                    true
                )
            }
        }
    }

    fun openRegisterScreen() {
        navController.navigate(Screen.Register.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
        }
    }

    BaseScreen(viewModel = viewModel) {
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
                    label = "Password"
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.login(username.trim(), password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = viewModel.isButtonEnabled.value
                ) {
                    Text(text = "Login")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    openRegisterScreen()
                }) {
                    Text(text = "Don't have an account? Register")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(navController = rememberNavController())
    }
}

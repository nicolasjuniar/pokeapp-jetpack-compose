package juniar.nicolas.pokeapp.jetpackcompose.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import juniar.nicolas.pokeapp.jetpackcompose.R
import juniar.nicolas.pokeapp.jetpackcompose.core.navigateScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.Screen

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    fun openMainScreen() {
        navController.navigateScreen(
            Screen.Main.route,
            Screen.Splash.route,
            true
        )
    }

    fun openLoginScreen() {
        navController.navigateScreen(
            Screen.Login.route,
            Screen.Splash.route,
            true
        )
    }

    LaunchedEffect(Unit) {
        viewModel.signal.collect {
            when (it) {
                is SplashSignal.NavigateToMain -> openMainScreen()
                is SplashSignal.NavigateToLogin -> openLoginScreen()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp),
                tint = Color.Unspecified
            )

            Spacer(Modifier.height(16.dp))

            Text("PokeApp", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(32.dp))

            CircularProgressIndicator(strokeWidth = 3.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(navController = rememberNavController())
    }
}

package juniar.nicolas.pokeapp.jetpackcompose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import juniar.nicolas.pokeapp.jetpackcompose.presentation.components.SimpleNotificationPermission
import juniar.nicolas.pokeapp.jetpackcompose.presentation.navigation.NavGraph

@AndroidEntryPoint
class PokeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.TRANSPARENT
            )
        )

        setContent {
            NavGraph()
            SimpleNotificationPermission()
        }
    }
}
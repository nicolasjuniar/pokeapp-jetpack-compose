package juniar.nicolas.pokeapp.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import juniar.nicolas.pokeapp.jetpackcompose.presentation.AuthScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.BottomNavigationScreen
import juniar.nicolas.pokeapp.jetpackcompose.presentation.DetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetailScreen()
        }
    }
}
package juniar.nicolas.pokeapp.jetpackcompose

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import juniar.nicolas.pokeapp.jetpackcompose.core.common.showToast
import juniar.nicolas.pokeapp.jetpackcompose.core.shared.ThemeViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.component.SimpleRuntimePermission
import juniar.nicolas.pokeapp.jetpackcompose.core.ui.theme.PokeAppJetpackComposeTheme

@AndroidEntryPoint
class PokeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val viewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            PokeAppJetpackComposeTheme(
                darkTheme = isDarkTheme
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    NavGraph()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        SimpleRuntimePermission(
                            permission = Manifest.permission.POST_NOTIFICATIONS,
                            onGranted = {
                                showToast("Notifications enabled. You can now view API logs via Chucker.")
                            },
                            onDenied = {
                                showToast("Enable notifications if you want to view API logs via Chucker.")
                            }
                        )
                    }
                }
            }
        }
    }
}

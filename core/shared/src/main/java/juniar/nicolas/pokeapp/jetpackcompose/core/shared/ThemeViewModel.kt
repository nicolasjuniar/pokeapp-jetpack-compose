package juniar.nicolas.pokeapp.jetpackcompose.core.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase.GetIsDarkThemeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> =
        getIsDarkThemeUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )
}

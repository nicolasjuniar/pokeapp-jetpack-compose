package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.ThemeRepository
import javax.inject.Inject

class UpdateDarkThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        repository.updateIsDarkTheme(isDarkTheme)
    }
}
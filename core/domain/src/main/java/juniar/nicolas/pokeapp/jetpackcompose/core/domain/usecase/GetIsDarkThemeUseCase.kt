package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsDarkThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getIsDarkTheme()
}
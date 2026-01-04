package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke() {
        repository.clearLoggedUsername()
    }
}
package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.SessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke() {
        repository.clearLoggedUsername()
    }
}
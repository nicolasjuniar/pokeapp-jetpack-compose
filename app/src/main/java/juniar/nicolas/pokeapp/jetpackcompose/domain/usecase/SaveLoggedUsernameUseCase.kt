package juniar.nicolas.pokeapp.jetpackcompose.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.SessionRepository
import javax.inject.Inject

class SaveLoggedUsernameUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(username: String) {
        repository.saveLoggedUsername(username)
    }
}
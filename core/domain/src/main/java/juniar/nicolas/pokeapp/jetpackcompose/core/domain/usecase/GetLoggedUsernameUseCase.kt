package juniar.nicolas.pokeapp.jetpackcompose.core.domain.usecase

import juniar.nicolas.pokeapp.jetpackcompose.core.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedUsernameUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    operator fun invoke(): Flow<String> = repository.getLoggedUsername()
}
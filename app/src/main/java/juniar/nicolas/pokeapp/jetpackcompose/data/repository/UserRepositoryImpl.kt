package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import juniar.nicolas.pokeapp.jetpackcompose.core.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.data.local.dao.UserDao
import juniar.nicolas.pokeapp.jetpackcompose.data.mapper.UserMapper
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper,
    private val dispatcher: AppDispatcher
) : UserRepository {

    override suspend fun getUsername(username: String, password: String): String? {
        return withContext(dispatcher.io) {
            userDao.getUsername(username, password)
        }
    }

    override suspend fun insertUser(user: User) {
        withContext(dispatcher.io) {
            val userEntity = userMapper.toEntity(user)
            userDao.insert(userEntity)
        }
    }

    override suspend fun isUsernameUnique(username: String): Boolean {
        return withContext(dispatcher.io) {
            userDao.getUserByUsername(username) == null
        }
    }
}

package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import juniar.nicolas.pokeapp.jetpackcompose.core.AppDispatcher
import juniar.nicolas.pokeapp.jetpackcompose.data.local.dao.UserDao
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.UserEntity
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

    override suspend fun getUserByUsername(username: String): UserEntity? {
        return withContext(dispatcher.io) {
            userDao.getUserByUsername(username)
        }
    }

    override suspend fun getUserProfilePicture(username: String): String {
        return withContext(dispatcher.io) {
            userDao.getProfilePictureByUsername(username)
        }
    }

    override suspend fun updateUserProfilePicture(uri: String, username: String) {
        withContext(dispatcher.io) {
            userDao.updateProfilePicture(uri, username)
        }
    }

    override suspend fun updateUser(user: UserEntity) {
        withContext(dispatcher.io) {
            userDao.updateUser(user)
        }
    }
}

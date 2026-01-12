package juniar.nicolas.pokeapp.jetpackcompose.data.repository

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.UserDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.UserEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper.UserMapper
import juniar.nicolas.pokeapp.jetpackcompose.core.data.repository.UserRepositoryImpl
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.User
import juniar.nicolas.pokeapp.jetpackcompose.data.TestAppDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val userDao: UserDao = mockk(relaxed = true)
    private val userMapper: UserMapper = mockk()

    @Test
    fun `getUsername returns value from dao`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        coEvery {
            userDao.getUsername("nico", "1234")
        } returns "nico"

        val result = repository.getUsername("nico", "1234")

        assertEquals("nico", result)
    }

    @Test
    fun `insertUser maps user and insert to dao`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        val user = User("nico", "1234")
        val entity = UserEntity("nico", "1234")

        every { userMapper.toEntity(user) } returns entity
        coEvery { userDao.insert(entity) } returns Unit

        repository.insertUser(user)
    }

    @Test
    fun `getUserByUsername returns mapped user when entity exists`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        val entity = UserEntity("nico", "asdf1234")
        val domain = User("nico", "asdf1234")

        coEvery { userDao.getUserByUsername("nico") } returns entity
        every { userMapper.toDomain(entity) } returns domain

        val result = repository.getUserByUsername("nico")

        assertEquals(domain, result)
    }

    @Test
    fun `getUserByUsername returns null when entity not found`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        coEvery { userDao.getUserByUsername("nico") } returns null

        val result = repository.getUserByUsername("nico")

        assertNull(result)
    }

    @Test
    fun `getUserProfilePicture returns value from dao`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        coEvery {
            userDao.getProfilePictureByUsername("nico")
        } returns "avatar.jpg"

        val result = repository.getUserProfilePicture("nico")

        assertEquals("avatar.jpg", result)
    }

    @Test
    fun `updateUserProfilePicture calls dao`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        coEvery {
            userDao.updateProfilePicture("uri", "nico")
        } returns Unit

        repository.updateUserProfilePicture("uri", "nico")
    }

    @Test
    fun `updateUser maps user and update dao`() = runTest {
        val dispatcher = TestAppDispatcher(testScheduler)
        val repository = UserRepositoryImpl(userDao, userMapper, dispatcher)

        val user = User("nico", "1234")
        val entity = UserEntity("nico", "1234")

        every { userMapper.toEntity(user) } returns entity
        coEvery { userDao.updateUser(entity) } returns Unit

        repository.updateUser(user)
    }
}


package juniar.nicolas.pokeapp.jetpackcompose.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.data.local.UserEntity
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.User

class UserMapper {

    fun toDomain(userEntity: UserEntity) = User(userEntity.username, userEntity.password)

    fun toEntity(user: User) = UserEntity(user.username, user.password)
}
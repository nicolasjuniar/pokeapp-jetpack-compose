package juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.UserEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.User


class UserMapper {

    fun toDomain(userEntity: UserEntity) = User(userEntity.username, userEntity.password)

    fun toEntity(user: User) = UserEntity(user.username, user.password)
}

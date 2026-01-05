package juniar.nicolas.pokeapp.jetpackcompose.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.FavoriteDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.PokemonDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.RemoteKeysDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.dao.UserDao
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.FavoriteEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.PokemonRemoteKeys
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PokemonEntity::class,
        PokemonRemoteKeys::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao
}

package juniar.nicolas.pokeapp.jetpackcompose.core.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.core.common.Constant.Companion.POKEMON_IMAGE_URL
import juniar.nicolas.pokeapp.jetpackcompose.core.data.dto.DetailPokemonResponse
import juniar.nicolas.pokeapp.jetpackcompose.core.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.PokemonAbility
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Stat

class PokemonMapper {
    fun toDomain(detailPokemonResponse: DetailPokemonResponse) = DetailPokemon(
        id = detailPokemonResponse.id,
        name = detailPokemonResponse.name,
        imageUrl = "$POKEMON_IMAGE_URL${detailPokemonResponse.id}.png",
        heightCm = detailPokemonResponse.height * 10,
        weightKg = detailPokemonResponse.weight / 10f,
        baseExperience = detailPokemonResponse.baseExperience,
        types = detailPokemonResponse.types.map { it.type.name },
        stats = detailPokemonResponse.stats.map { Stat(it.stat.name, it.baseStat) },
        abilities = detailPokemonResponse.abilities.map {
            PokemonAbility(
                it.ability.name, it.isHidden
            )
        },
    )

    fun toDomain(pokemonEntity: PokemonEntity) = Pokemon(pokemonEntity.name, pokemonEntity.id)
}

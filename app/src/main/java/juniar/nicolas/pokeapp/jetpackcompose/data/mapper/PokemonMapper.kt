package juniar.nicolas.pokeapp.jetpackcompose.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.core.Constant.Companion.POKEMON_IMAGE_URL
import juniar.nicolas.pokeapp.jetpackcompose.data.dto.DetailPokemonResponse
import juniar.nicolas.pokeapp.jetpackcompose.data.local.entity.PokemonEntity
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Pokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Stat

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
        abilities = detailPokemonResponse.abilities,
    )

    fun toDomain(pokemonEntity: PokemonEntity) = Pokemon(pokemonEntity.name, pokemonEntity.id)
}
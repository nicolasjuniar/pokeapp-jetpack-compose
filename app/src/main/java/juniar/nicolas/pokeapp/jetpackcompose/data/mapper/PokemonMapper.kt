package juniar.nicolas.pokeapp.jetpackcompose.data.mapper

import juniar.nicolas.pokeapp.jetpackcompose.core.Constant.Companion.POKEMON_IMAGE_URL
import juniar.nicolas.pokeapp.jetpackcompose.data.dto.DetailPokemonResponse
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.DetailPokemon
import juniar.nicolas.pokeapp.jetpackcompose.domain.model.Stat

fun DetailPokemonResponse.toDomain(): DetailPokemon {
    return DetailPokemon(
        id = id,
        name = name,
        imageUrl = "$POKEMON_IMAGE_URL$id.png",
        heightCm = height * 10,
        weightKg = weight / 10f,
        baseExperience = baseExperience,
        types = types.map { it.type.name },
        stats = stats.map { Stat(it.stat.name, it.baseStat) },
        abilities = abilities.map { it.ability.name + if (it.isHidden) " (hidden)" else "" },
    )
}
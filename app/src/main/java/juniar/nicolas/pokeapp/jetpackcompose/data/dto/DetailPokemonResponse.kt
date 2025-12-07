package juniar.nicolas.pokeapp.jetpackcompose.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailPokemonResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("base_experience")
    val baseExperience: Int,
    @SerialName("types")
    val types: List<PokemonType>,
    @SerialName("stats")
    val stats: List<PokemonStat>,
    @SerialName("abilities")
    val abilities: List<PokemonAbility>,
)

@Serializable
data class PokemonType(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: NamedResource
)

@Serializable
data class PokemonStat(
    @SerialName("base_stat")
    val baseStat: Int,
    @SerialName("stat")
    val stat: NamedResource
)

@Serializable
data class PokemonAbility(
    @SerialName("ability")
    val ability: NamedResource,
    @SerialName("is_hidden")
    val isHidden: Boolean
)

@Serializable
data class NamedResource(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)

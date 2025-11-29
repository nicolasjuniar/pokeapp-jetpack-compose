package juniar.nicolas.pokeapp.jetpackcompose.data.dto

import com.google.gson.annotations.SerializedName

data class DetailPokemonResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("base_experience")
    val baseExperience: Int,
    @SerializedName("types")
    val types: List<PokemonType>,
    @SerializedName("stats")
    val stats: List<PokemonStat>,
    @SerializedName("abilities")
    val abilities: List<PokemonAbility>,
)

data class PokemonType(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: NamedResource
)

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("stat")
    val stat: NamedResource
)

data class PokemonAbility(
    @SerializedName("ability")
    val ability: NamedResource,
    @SerializedName("is_hidden")
    val isHidden: Boolean
)

data class NamedResource(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
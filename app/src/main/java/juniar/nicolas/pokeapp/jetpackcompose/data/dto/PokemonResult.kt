package juniar.nicolas.pokeapp.jetpackcompose.data.dto

import com.google.gson.annotations.SerializedName

data class PokemonResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

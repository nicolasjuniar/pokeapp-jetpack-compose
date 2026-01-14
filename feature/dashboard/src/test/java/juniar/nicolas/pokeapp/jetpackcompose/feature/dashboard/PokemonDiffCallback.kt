package juniar.nicolas.pokeapp.jetpackcompose.feature.dashboard

import androidx.recyclerview.widget.DiffUtil
import juniar.nicolas.pokeapp.jetpackcompose.core.domain.model.Pokemon

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.pokedexNumber == newItem.pokedexNumber
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}
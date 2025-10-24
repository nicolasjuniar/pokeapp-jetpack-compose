package juniar.nicolas.pokeappjetpackcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResult
import juniar.nicolas.pokeappjetpackcompose.data.network.ApiClient

class PokemonPagingSource : PagingSource<Int, PokemonResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        return try {
            val offset = params.key ?: 0
            val limit = 10

            val response = ApiClient.api.getPokemons(limit = limit, offset = offset)
            val pokemons = response.results

            LoadResult.Page(
                data = pokemons,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (pokemons.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition
    }
}
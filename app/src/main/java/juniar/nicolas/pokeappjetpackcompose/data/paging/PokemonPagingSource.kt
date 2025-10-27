package juniar.nicolas.pokeappjetpackcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import juniar.nicolas.pokeappjetpackcompose.data.model.PokemonResult
import juniar.nicolas.pokeappjetpackcompose.data.remote.PokeApiClient

class PokemonPagingSource : PagingSource<Int, PokemonResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val offset = params.key ?: 0
        return try {
            val response = PokeApiClient.api.getPokemonList(limit = 10, offset = offset)
            val nextKey = if (response.next == null) null else offset + 10
            LoadResult.Page(
                data = response.results,
                prevKey = if (offset == 0) null else offset - 10,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? = null
}
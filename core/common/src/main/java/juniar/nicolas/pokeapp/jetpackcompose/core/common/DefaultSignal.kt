package juniar.nicolas.pokeapp.jetpackcompose.core.common

sealed interface DefaultSignal {
    data class ShowToast(val message: String) : DefaultSignal
}

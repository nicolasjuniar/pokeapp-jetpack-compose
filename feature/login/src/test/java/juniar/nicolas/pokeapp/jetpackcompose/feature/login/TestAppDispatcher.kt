package juniar.nicolas.pokeapp.jetpackcompose.feature.login

import juniar.nicolas.pokeapp.jetpackcompose.core.common.AppDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher

class TestAppDispatcher(
    scheduler: TestCoroutineScheduler
) : AppDispatcher {

    private val dispatcher = StandardTestDispatcher(scheduler)

    override val main: CoroutineDispatcher = dispatcher
    override val io: CoroutineDispatcher = dispatcher
    override val default: CoroutineDispatcher = dispatcher
}


package juniar.nicolas.pokeapp.jetpackcompose.core.shared

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PreviewCameraViewModelTest {

    private val viewModel = PreviewCameraViewmodel()

    @Test
    fun `initial photo uri is empty`() = runTest {
        assertEquals("", viewModel.photoUri.first())
    }

    @Test
    fun `save photo uri updates state`() = runTest {
        val uri = "content://camera/photo.jpg"
        viewModel.savePhotoUri(uri)
        assertEquals(uri, viewModel.photoUri.first())
    }

    @Test
    fun `clear resets photo uri to empty`() = runTest {
        val uri = "content://camera/photo.jpg"
        viewModel.savePhotoUri(uri)
        viewModel.clear()
        assertEquals("", viewModel.photoUri.first())
    }
}

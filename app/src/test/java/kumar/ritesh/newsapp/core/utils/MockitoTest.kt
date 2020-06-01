package kumar.ritesh.newsapp.core.utils

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class MockitoTest {

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }
}
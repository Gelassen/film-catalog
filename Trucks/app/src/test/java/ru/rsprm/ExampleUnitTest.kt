package ru.rsprm

import org.junit.Test

import org.junit.Assert.*
import ru.rsprm.di.DaggerTestAppComponent
import ru.rsprm.di.TestAppComponent

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val component = DaggerTestAppComponent.builder().build()

        assertEquals(4, 2 + 2)
    }
}

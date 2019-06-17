package ru.rsprm

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.rsprm.screens.add.AddTruckPresenter
import ru.rsprm.screens.add.IAddTruckContract
import javax.inject.Inject

class AddTruckPresenterTest : BaseTest() {

    @Mock
    lateinit var view: IAddTruckContract.View

    @Inject
    lateinit var subject: AddTruckPresenter

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        component.inject(this)
    }

    @Test
    fun testOnStart_serverIsOk_callViewOnCreate() {
        serverRule.getDispatcher().setOkAddTruckResponse()
        subject.onStart(view, null)

        subject.onSubmit(10, "new truck", "10", "comment")

        verify(view).onCreate(any())
    }

    @Test
    fun testOnStart_serverIsNotOk_showError() {
        serverRule.getDispatcher().setAddTrucksBadGateway()
        subject.onStart(view, null)

        subject.onSubmit(10, "new truck", "10", "comment")

        verify(view).onError("Сервер недоступен")
    }

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()
}

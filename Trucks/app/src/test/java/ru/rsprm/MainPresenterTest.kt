package ru.rsprm

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.robolectric.RuntimeEnvironment
import ru.rsprm.model.Truck
import ru.rsprm.network.NetworkUtils
import ru.rsprm.screens.list.IListContract
import ru.rsprm.screens.list.MainPresenter
import javax.inject.Inject

class MainPresenterTest: BaseTest() {

    @Mock
    lateinit var networkUtils : NetworkUtils

    @Mock
    lateinit var view: IListContract.View

    @Captor
    lateinit var argumentCaptor: ArgumentCaptor<List<Truck>>

    @Inject
    lateinit var subject: MainPresenter

    @Before
    override fun setUp() {
        super.setUp()

        component.inject(this)

        `when`(networkUtils.isConnected(RuntimeEnvironment.application))
            .thenReturn(false)
    }

    @Test
    fun testOnStart_everythingIsOk_getValue() {
        serverRule.getDispatcher().setOkTrucksResponse()

        subject.onStart(view)

        verify(view).onTrucks(ArgumentMatchers.anyList())
        verify(view).hideProgressIndicator()
        verify(view).hideEmptyList()
    }

    @Test
    fun testOnStart_badGateway_showServerIsNotAvailableMessage() {
        serverRule.getDispatcher().setTrucksBadGatewayResponse()

        subject.onStart(view)

        verify(view).hideProgressIndicator()
        verify(view).onError("Сервер недоступен")
    }

    @Test
    fun testOnStart_oneItemFromThreeIsNotValid_onTrucksInputHasSizeTwo() {
        serverRule.getDispatcher().setTrucksInvalidResponse()

        subject.onStart(view)

        verify(view).onTrucks(capture(argumentCaptor))
        Assert.assertEquals(2, argumentCaptor.value.size)
    }

    // emulate long connectivity / connection timeout reset

    // emulate lack of internet

    // emulate redirect (302 code)

    fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}

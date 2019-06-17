package ru.rsprm.utils


import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.RecordedRequest

import java.io.File

class AppServerDispatcher : Dispatcher() {

    var trucksMockResponse: MockResponse? = null

    var truckAddMockResponse: MockResponse? = null

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        var mockResponse = MockResponse().setResponseCode(200)
        val path = request.path
        if (path == URL_TRUCKS) {
            mockResponse = getTrucksResponse()
        } else if (path == URL_TRUCKS_ADD) {
            mockResponse = getTruckAddResponse()
        }
        return mockResponse
    }

    // test, debug only

    fun setOkTrucksResponse() {
        trucksMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_response.json"))
    }

    fun setTrucksBadGatewayResponse() {
        trucksMockResponse = MockResponse()
            .setResponseCode(502)
    }

    fun setTrucksInvalidResponse() {
        trucksMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_partly_valid_response.json"))
    }


    fun getTrucksResponse(): MockResponse {
        if (trucksMockResponse == null) {
            setOkTrucksResponse()
        }
        return trucksMockResponse!!
    }

    fun getTruckAddResponse(): MockResponse {
        if (truckAddMockResponse == null) {
            setOkAddTruckResponse()
        }
        return truckAddMockResponse!!
    }

    fun setOkAddTruckResponse() {
        truckAddMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_add_truck_response.json"))
    }

    fun setAddTrucksBadGateway() {
        truckAddMockResponse = MockResponse()
            .setResponseCode(502)
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    companion object {

        private val URL_TRUCKS = "/test/trucks"

        private val URL_TRUCKS_ADD = "/test/truck/add"
    }
}

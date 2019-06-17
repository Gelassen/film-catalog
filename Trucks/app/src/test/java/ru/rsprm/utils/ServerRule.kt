package ru.rsprm.utils

import android.util.Log
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import ru.rsprm.App

import java.io.IOException
import java.net.URL

class ServerRule : ExternalResource() {

    private lateinit var server: MockWebServer

    private lateinit var dispatcher: AppServerDispatcher

    fun getUrl(): HttpUrl {
        return server.url("/test/")
    }

    fun getDispatcher(): AppServerDispatcher {
        return dispatcher
    }

    @Throws(Throwable::class)
    override fun before() {
        super.before()

        dispatcher = AppServerDispatcher()

        server = MockWebServer()
        server.start()
        server.setDispatcher(dispatcher)
    }

    override fun after() {
        super.after()
        try {
            server.shutdown()
        } catch (e: IOException) {
            Log.e(App.TAG, "Failed to shutdown server", e)
        }

    }
}

package com.leehendryp.androidcase.dataentry.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.leehendryp.androidcase.core.GeoApi
import com.leehendryp.androidcase.core.TicTacApi
import com.leehendryp.androidcase.dataentry.core.MainCoroutineRule
import com.leehendryp.androidcase.dataentry.core.ResponseType.CLIENT_ERROR
import com.leehendryp.androidcase.dataentry.core.ResponseType.SERVER_ERROR
import com.leehendryp.androidcase.dataentry.core.ResponseType.SUCCESS
import com.leehendryp.androidcase.dataentry.core.createRetrofitInstance
import com.leehendryp.androidcase.dataentry.core.setResponse
import com.leehendryp.androidcase.dataentry.data.CouldNotFetchAnttPrices
import com.leehendryp.androidcase.dataentry.data.CouldNotFetchRouteDetails
import com.leehendryp.androidcase.dataentry.data.DTOs
import com.squareup.okhttp.mockwebserver.MockWebServer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// FIXME: Lee Mar 26, 2020: runBlockingTest API has known issues (#1204 and #1626). Refactor tests when API is fixed
// For more info, check: https://github.com/Kotlin/kotlinx.coroutines/issues/1204
// For more info, check: https://github.com/Kotlin/kotlinx.coroutines/issues/1626

@ExperimentalCoroutinesApi
class RemoteDataSourceImplTest {
    companion object {
        private const val ROUTE_DETAILS_RESPONSE_JSON = "RouteDetailsResponse.json"
        private const val ANTT_PRICES_RESPONSE_JSON = "AnttPricesResponse.json"
    }

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var geoApiServer: MockWebServer
    private lateinit var ticTacApiServer: MockWebServer

    private lateinit var geoApi: GeoApi
    private lateinit var ticTacApi: TicTacApi

    private lateinit var remoteSource: RemoteDataSourceImpl

    @Before
    fun `set up`() {
        geoApiServer = MockWebServer()
        ticTacApiServer = MockWebServer()
        instantiateGeoApiMockFromMockServer()
        instantiateTicTacApiMockFromMockServer()
        remoteSource = RemoteDataSourceImpl(geoApi, ticTacApi)
    }

    private fun instantiateGeoApiMockFromMockServer() {
        val retrofit = geoApiServer.createRetrofitInstance()
        geoApi = retrofit.create(GeoApi::class.java)
    }

    private fun instantiateTicTacApiMockFromMockServer() {
        val retrofit = ticTacApiServer.createRetrofitInstance()
        ticTacApi = retrofit.create(TicTacApi::class.java)
    }

    @Test
    fun `should successfully fetch route response from valid request`() = runBlocking {
        geoApiServer.setResponse(SUCCESS, ROUTE_DETAILS_RESPONSE_JSON)

        val result = remoteSource.getRouteDetailsFrom(DTOs.infoProvidedByDriver)

        assertThat(result, equalTo(DTOs.routeDetails))
    }

    @Test(expected = CouldNotFetchRouteDetails::class)
    fun `should throw exception if server fails to respond`() {
        runBlocking {
            geoApiServer.setResponse(SERVER_ERROR)
            remoteSource.getRouteDetailsFrom(DTOs.infoProvidedByDriver)
        }
    }

    @Test(expected = CouldNotFetchRouteDetails::class)
    fun `should throw exception if app fails to make request`() {
        runBlocking {
            geoApiServer.setResponse(CLIENT_ERROR)
            remoteSource.getRouteDetailsFrom(DTOs.infoProvidedByDriver)
        }
    }

    @Test
    fun `should successfully get ANTT prices response from valid request`() = runBlocking {
        ticTacApiServer.setResponse(SUCCESS, ANTT_PRICES_RESPONSE_JSON)

        val result = remoteSource.getAnttPrices(DTOs.infoForAntt)

        assertThat(result, equalTo(DTOs.anttPrices))
    }

    @Test(expected = CouldNotFetchAnttPrices::class)
    fun `should throw exception if server fails to respond to ANTT prices request`() {
        runBlocking {
            ticTacApiServer.setResponse(SERVER_ERROR)
            remoteSource.getAnttPrices(DTOs.infoForAntt)
        }
    }

    @Test(expected = CouldNotFetchAnttPrices::class)
    fun `should throw exception if app fails to make ANTT prices request`() {
        runBlocking {
            ticTacApiServer.setResponse(CLIENT_ERROR)
            remoteSource.getAnttPrices(DTOs.infoForAntt)
        }
    }

    @After
    fun `tear down`() {
        geoApiServer.shutdown()
        ticTacApiServer.shutdown()
    }
}
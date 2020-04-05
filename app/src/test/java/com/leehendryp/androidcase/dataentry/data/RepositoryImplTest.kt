package com.leehendryp.androidcase.dataentry.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.leehendryp.androidcase.dataentry.core.MainCoroutineRule
import com.leehendryp.androidcase.dataentry.data.local.LocalDataSource
import com.leehendryp.androidcase.dataentry.data.remote.RemoteDataSource
import com.leehendryp.androidcase.dataentry.domain.mapIntoRouteWithAnttPrices
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// FIXME: Lee Mar 26, 2020: runBlockingTest API has a known issue (#1204 and #1626). Refactor tests when API is fixed
// For more info, check: https://github.com/Kotlin/kotlinx.coroutines/issues/1204
// For more info, check: https://github.com/Kotlin/kotlinx.coroutines/issues/1626

@ExperimentalCoroutinesApi
class RepositoryImplTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteSource: RemoteDataSource = spyk()
    private val localSource: LocalDataSource = spyk()

    private lateinit var repo: RepositoryImpl

    @Before
    fun `set up`() {
        repo = RepositoryImpl(remoteSource, localSource)
    }

    @Test
    fun `should create route with ANTT prices object from remote datch fetch request based on info provided by driver`() =
        runBlocking {
            val infoProvidedByDriver = DTOs.infoProvidedByDriver
            val routeDetails = DTOs.routeDetails
            val infoForAntt = DTOs.infoForAntt
            val anttPrices = DTOs.anttPrices
            val routeWithAnttPrices = routeDetails.mapIntoRouteWithAnttPrices(anttPrices)

            coEvery { remoteSource.getRouteDetailsFrom(infoProvidedByDriver) } returns routeDetails
            coEvery { remoteSource.getAnttPrices(infoForAntt) } returns anttPrices

            val result = repo.getRouteWithAnttPricesFrom(infoProvidedByDriver)

            coVerifyOrder {
                remoteSource.getRouteDetailsFrom(infoProvidedByDriver)
                remoteSource.getAnttPrices(infoForAntt)
            }
            coVerify(exactly = 1) { localSource.save(result) }
            coVerify(exactly = 0) { localSource.getHistory() }

            assertThat(result, equalTo(routeWithAnttPrices))
        }

    @Test
    fun `should save route with ANTT prices object fetched from remote source fetch`() =
        runBlocking {
            val infoProvidedByDriver = DTOs.infoProvidedByDriver
            val routeDetails = DTOs.routeDetails
            val anttPrices = DTOs.anttPrices

            coEvery { remoteSource.getRouteDetailsFrom(any()) } returns routeDetails
            coEvery { remoteSource.getAnttPrices(any()) } returns anttPrices

            val result = repo.getRouteWithAnttPricesFrom(infoProvidedByDriver)

            coVerify(exactly = 1) { localSource.save(result) }
            coVerify(exactly = 0) { localSource.getHistory() }
        }

    @Test
    fun `should return array of route with ANTT prices objects from local source upon call`() =
        runBlocking {
            repo.getHistory()
            coVerify(exactly = 1) { localSource.getHistory() }
            coVerify(exactly = 0) { localSource.save(any()) }
            coVerify(exactly = 0) { remoteSource.getRouteDetailsFrom(any()) }
            coVerify(exactly = 0) { remoteSource.getAnttPrices(any()) }
        }
}
package com.leehendryp.androidcase.dataentry.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.leehendryp.androidcase.dataentry.core.MainCoroutineRule
import com.leehendryp.androidcase.dataentry.data.DTOs
import com.leehendryp.androidcase.dataentry.domain.Repository
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Default
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Error
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Loading
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Success
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// FIXME: Lee Mar 26, 2020: runBlockingTest API has a known issue (#1204 and #1626). Refactor tests when API is fixed
// For more info, check: https://github.com/Kotlin/kotlinx.coroutines/issues/1204
// For more info, check: https://github.com/Kotlin/kotlinx.coroutines/issues/1626

@ExperimentalCoroutinesApi
class DataEntryViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DataEntryViewModel
    private var repo: Repository = mockk()

    private lateinit var mockedObserver: Observer<DataEntryState>
    private val stateSlots = mutableListOf<DataEntryState>()

    @Before
    @Test
    fun `set up`() {
        viewModel = DataEntryViewModel(repo)
        createMockedObserver()
        observeViewModelState()
    }

    private fun createMockedObserver() {
        mockedObserver = spyk(Observer { Unit })
    }

    private fun observeViewModelState() {
        viewModel.state.observeForever(mockedObserver)
    }

    @Test
    fun `should update state to Success with route details and ANTT prices data upon successfully exchanging data via repo based on info provided by driver`() =
        runBlocking {
            coEvery { repo.getRouteDetailsFrom(any()) } returns DTOs.routeWithAnttPrices

            viewModel.getRouteDetailsFrom(DTOs.infoProvidedByDriver)

            verify(exactly = 3) { mockedObserver.onChanged(capture(stateSlots)) }

            verifyOrder {
                mockedObserver.onChanged(Default)
                mockedObserver.onChanged(Loading)
                mockedObserver.onChanged(any<Success>())
            }

            assertThat(
                (stateSlots[2] as Success).data,
                equalTo(DTOs.routeWithAnttPrices)
            )
        }

    @Test
    fun `should update state to Error with proper throwable upon failing to exchanging data via repo`() =
        runBlocking {
            val error = Throwable()

            coEvery { repo.getRouteDetailsFrom(any()) } throws error

            viewModel.getRouteDetailsFrom(DTOs.infoProvidedByDriver)

            verify(exactly = 3) { mockedObserver.onChanged(capture(stateSlots)) }

            verifyOrder {
                mockedObserver.onChanged(Default)
                mockedObserver.onChanged(Loading)
                mockedObserver.onChanged(any<Error>())
            }

            assertThat((stateSlots[2] as Error).error, equalTo(error))
        }

    @After
    fun `tear down`() {
        stateSlots.clear()
    }
}
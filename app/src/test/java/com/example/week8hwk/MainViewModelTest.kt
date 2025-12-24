package com.example.week8hwk


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week8hwk.data.Repository
import com.example.week8hwk.data.database.AppDatabase
import com.example.week8hwk.data.model.Car
import com.example.week8hwk.data.network.ApiService
import com.example.week8hwk.mvi.CarAction
import com.example.week8hwk.mvi.CarReducer
import com.example.week8hwk.mvi.CarState
import com.example.week8hwk.mvi.UiState
import com.example.week8hwk.ui.theme.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.assertEquals


private val fakeCar1 = Car(
    make = "Toyota",
    model = "Corolla",
    year = 2020,
    carClass = "Sedan",
    transmission = "Automatic",
    drive = "FWD")

private val fakeCar2 = Car(
    make = "Honda",
    model = "Civic",
    year = 2021,
    carClass = "Sedan",
    transmission = "CVT",
    drive = "FWD"
)

private val fakeCar3 = Car(
    make = "Ford",
    model = "Mustang",
    year = 2019,
    carClass = "Sports",
    transmission = "Manual",
    drive = "RWD"
)





private class FakeApiService : ApiService {
    var cars: List<Car> = emptyList()
    var shouldThrowError = false

    override suspend fun getCars(make: String): List<Car> {
        if (shouldThrowError) throw Exception("Network error")

        return cars
    }


}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [24])

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    private lateinit var fakeApiService: FakeApiService

    private lateinit var repository: Repository

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeDb: AppDatabase






    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()

        fakeDb = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java)
            .allowMainThreadQueries().build()

        fakeApiService = FakeApiService()
        repository = Repository(
            apiService = fakeApiService,
            db = fakeDb)


    }

    @After
    fun teardown() {
        fakeDb.close()
        Dispatchers.resetMain()

    }

    @Test
    fun `refreshHomePage updates car list with API data` () = runTest {

        fakeApiService.cars = listOf(fakeCar1, fakeCar2, fakeCar3)

        viewModel = MainViewModel(repository)

        viewModel.refreshHomePage()
        advanceUntilIdle()

        val carsInDb = fakeDb.dao().getAllCars()

        assertEquals(3, carsInDb.size)

        val carsMap = carsInDb.associateBy { it.model }


        assertEquals("Toyota", carsMap["Corolla"]?.make)
        assertEquals(2021, carsMap["Civic"]?.year)
        assertEquals("RWD", carsMap["Mustang"]?.drive)




    }

    @Test
    fun `FetchCar sets state to Loading`() {
        val oldState = CarState()
        val action = CarAction.FetchCar

        val newState = CarReducer(oldState, action)

        assertEquals(UiState.Loading, newState.uiState)
    }
}
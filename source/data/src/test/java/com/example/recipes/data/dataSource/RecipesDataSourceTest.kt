package com.example.recipes.data.dataSource

import com.example.recipes.data.domain.core.ErrorState
import com.example.recipes.data.domain.core.ResponseState
import com.example.recipes.data.network.retrofit.implement.RecipesNetwork
import com.example.recipes.data.network.retrofit.response.RecipesDetailResponse
import com.example.recipes.data.network.retrofit.response.RecipesResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class RecipesDataSourceTest {

    @Mock
    private lateinit var network: RecipesNetwork

    private lateinit var recipesDataSource: RecipesDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        recipesDataSource = RecipesDataSource()
        recipesDataSource.network = network
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `getRecipes should emit Loading and Success states when network response is successful`() = runBlocking {
        // Arrange
        val mockRecipesResponse = createMockRecipesResponse()
        `when`(network.getRecipes()).thenReturn(createMockSuccessfulResponse(mockRecipesResponse))

        // Act
        val states = mutableListOf<ResponseState<Any?>>()
        val job = launch {
            recipesDataSource.getRecipes().collect { state ->
                states.add(state)
            }
        }

        job.join() // Esperar hasta que todas las coroutines se completen

        // Assert
        assertEquals(2, states.size) // Expecting Loading and Success states
        assertTrue(states[0] is ResponseState.Loading)
        assertTrue(states[1] is ResponseState.Success)

        val successState = states[1] as ResponseState.Success
        assertEquals(mockRecipesResponse.recetas.size, successState.data)

        job.cancel() // Cancelar la coroutine para liberar recursos
    }

    @Test
    fun `getRecipes should emit Loading and Error states when network response is not successful`() = runBlocking {
        // Arrange
        `when`(network.getRecipes()).thenReturn(createMockErrorResponse())

        // Act
        val states = mutableListOf<ResponseState<Any?>>()
        recipesDataSource.getRecipes().collect { state ->
            states.add(state)
        }

        // Assert
        assertEquals(2, states.size) // Expecting Loading and Error states
        assertTrue(states[0] is ResponseState.Loading)
        assertTrue(states[1] is ResponseState.Error)

        val errorState = states[1] as ResponseState.Error
        assertEquals(ErrorState.Network, errorState.error)
    }

    // Helper methods to create mock network responses

    private fun createMockSuccessfulResponse(data: RecipesResponse): Response<RecipesResponse> {
        return Response.success(data)
    }

    private fun createMockErrorResponse(): Response<RecipesResponse> {
        return Response.error(500, ResponseBody.create("application/json".toMediaTypeOrNull(), ""))
    }

    private fun createMockRecipesResponse(): RecipesResponse {
        val recetas = listOf(
            RecipesDetailResponse(
                nombre = "Receta 1",
                dificultad = "Fácil",
                tiempo = 30,
                calificacion = 4.5,
                porcion = "2",
                descripcion = "Descripción de la receta 1",
                imagen = "imagen_receta_1.jpg",
                lat = 0.0,
                lon = 0.0,
                ingredientes = listOf("Ingrediente 1", "Ingrediente 2")
            ),
            RecipesDetailResponse(
                nombre = "Receta 2",
                dificultad = "Media",
                tiempo = 60,
                calificacion = 4.2,
                porcion = "4",
                descripcion = "Descripción de la receta 2",
                imagen = "imagen_receta_2.jpg",
                lat = 0.0,
                lon = 0.0,
                ingredientes = listOf("Ingrediente 3", "Ingrediente 4")
            )
        )
        return RecipesResponse(recetas)
    }
}

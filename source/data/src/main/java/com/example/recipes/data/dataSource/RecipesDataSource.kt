package com.example.recipes.data.dataSource

import com.example.recipes.data.domain.Receta
import com.example.recipes.data.domain.core.ErrorState
import com.example.recipes.data.domain.core.ResponseState
import com.example.recipes.data.extensions.getResponseError
import com.example.recipes.data.extensions.validateRetryCount
import com.example.recipes.data.extensions.validateServerResponseCode
import com.example.recipes.data.network.retrofit.RetrofitInstance
import com.example.recipes.data.network.retrofit.implement.RecipesNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipesDataSource {

    var network = RetrofitInstance.getInstance().create(RecipesNetwork::class.java)

    @Throws(SecurityException::class)
    suspend fun getRecipes(): Flow<ResponseState<Any?>> {
        return flow {
            var intents = 1
            try {
                emit(ResponseState.Loading)
                val networkResponse = network.getRecipes()
                if (networkResponse.code().validateServerResponseCode()) {
                    networkResponse.body()?.let {
                        emit(ResponseState.Success(
                            it.recetas.map {
                                Receta(
                                    nombre = it.nombre,
                                    dificultad = it.dificultad,
                                    tiempo = it.tiempo,
                                    calificacion = it.calificacion,
                                    porcion = it.porcion,
                                    descripcion = it.descripcion,
                                    imagen = it.imagen,
                                    lat = it.lat,
                                    lon = it.lon,
                                    ingredientes = it.ingredientes
                                )
                            }
                        )
                        )
                    }
                } else {
                    emit(networkResponse.getResponseError())
                }
            } catch (securityException: SecurityException) {
                throw securityException
            } catch (exception: Exception) {
                while (intents.validateRetryCount()) {
                    intents += 1
                    getRecipes()
                }
                emit(ResponseState.Error(ErrorState.Network))
            }
        }
    }
}
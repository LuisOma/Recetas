package com.example.recipes.data.network.retrofit.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipesResponse(
    var recetas: List<RecipesDetailResponse>
): Parcelable

@Parcelize
data class RecipesDetailResponse(
    var nombre: String,
    var dificultad: String,
    var tiempo: Int,
    var calificacion: Double,
    var porcion : String,
    var descripcion: String,
    var imagen: String,
    var lat: Double,
    var lon: Double,
    var ingredientes: List<String>
): Parcelable

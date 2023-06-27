package com.example.recipes.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RecipesModel(
    val recetas: List<Receta>,
)

@Parcelize
data class Receta(
    val nombre: String,
    val dificultad: String,
    val tiempo: Int,
    var calificacion: Double,
    var porcion : String,
    val descripcion: String,
    var imagen: String,
    val lat: Double,
    val lon: Double,
    val ingredientes: List<String>
): Parcelable

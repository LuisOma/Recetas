package com.example.recipes.data

import com.example.recipes.data.domain.Receta
import com.example.recipes.data.domain.RecipesModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class RecipesModelTest {
    @Test
    fun testRecetasModel() {
        val recetas = listOf(
            Receta("Receta 1", "Fácil", 30, 4.5, "4 porciones", "Descripción de la receta 1",
                "imagen1.jpg", 1.0, 2.0, listOf("Ingrediente 1", "Ingrediente 2")),
            Receta("Receta 2", "Media", 45, 3.7, "6 porciones", "Descripción de la receta 2",
                "imagen2.jpg", 3.0, 4.0, listOf("Ingrediente 3", "Ingrediente 4", "Ingrediente 5"))
        )

        val recipesModel = RecipesModel(recetas)

        assertEquals(recetas, recipesModel.recetas)
    }
}

class RecetaTest {
    @Test
    fun testReceta() {
        val receta = Receta("Receta 1", "Fácil", 30, 4.5, "4 porciones", "Descripción de la receta 1",
            "imagen1.jpg", 1.0, 2.0, listOf("Ingrediente 1", "Ingrediente 2"))

        assertEquals("Receta 1", receta.nombre)
        assertEquals("Fácil", receta.dificultad)
        assertEquals(30, receta.tiempo)
        assertEquals(4.5, receta.calificacion, 0.01)
        assertEquals("4 porciones", receta.porcion)
        assertEquals("Descripción de la receta 1", receta.descripcion)
        assertEquals("imagen1.jpg", receta.imagen)
        assertEquals(1.0, receta.lat, 0.01)
        assertEquals(2.0, receta.lon, 0.01)
        assertEquals(listOf("Ingrediente 1", "Ingrediente 2"), receta.ingredientes)
    }
}

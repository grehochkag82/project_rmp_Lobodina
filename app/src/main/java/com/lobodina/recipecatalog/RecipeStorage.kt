package com.lobodina

import android.content.Context
import com.lobodina.recipecatalog.Recipe
import java.io.File

class RecipeStorage(context: Context) {
    private val file = File(context.filesDir, "recipes.txt")

    fun getAll(): List<Recipe> {
        if (!file.exists()) return getDefaultRecipes()
        val lines = file.readLines()
        val list = mutableListOf<Recipe>()
        for (line in lines) {
            val parts = line.split("||")
            if (parts.size == 4) {
                val id = parts[0].toLongOrNull() ?: continue
                list.add(Recipe(id, parts[1], parts[2], parts[3]))
            }
        }
        return list
    }

    fun add(recipe: Recipe) {
        val current = getAll().toMutableList()
        current.add(recipe)
        saveAll(current)
    }

    fun getById(id: Long): Recipe? = getAll().find { it.id == id }

    private fun saveAll(recipes: List<Recipe>) {
        val content = StringBuilder()
        for (r in recipes) {
            content.append("${r.id}||${r.name}||${r.ingredients}||${r.prepTime}\n")
        }
        file.writeText(content.toString())
    }

    private fun getDefaultRecipes(): List<Recipe> {
        val list = listOf(
            Recipe(1, "Оладьи", "Мука: 200г, Яйца: 2шт, Молоко: 150мл, Сахар: 2ст.л", "20 минут"),
            Recipe(2, "Салат Цезарь", "Курица: 200г, Листья салата: 50г, Помидоры: 2шт, Соус: 30мл", "15 минут")
        )
        saveAll(list)
        return list
    }
}
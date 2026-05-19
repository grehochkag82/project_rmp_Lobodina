package com.lobodina.recipecatalog

data class Recipe(
    val id: Long,
    val name: String,
    val ingredients: String,
    val prepTime: String
)
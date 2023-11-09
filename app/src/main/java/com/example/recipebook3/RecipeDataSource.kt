package com.example.recipebook3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class RecipeDataSource(context: Context) {
    private val dbHelper: RecipeDBHelper = RecipeDBHelper(context)
    private var database: SQLiteDatabase? = null

    // Open the database for read/write operations
    @Throws(SQLException::class)
    fun open() {
        try {
            database = dbHelper.writableDatabase
        } catch (e: SQLException) {
            Log.e("RecipeDataSource", "Error opening the database: $e")
            throw e
        }
    }

    // Close the database
    fun close() {
        dbHelper.close()
    }

    // Insert a new recipe into the database
    fun insertRecipe(recipe: Recipe): Long {
        val values = ContentValues()
        values.put(RecipeDBHelper.RecipeTable.COLUMN_NAME, recipe.name)
        values.put(RecipeDBHelper.RecipeTable.COLUMN_TITLE, recipe.title)
        values.put(RecipeDBHelper.RecipeTable.COLUMN_DESCRIPTION, recipe.description)
        values.put(RecipeDBHelper.RecipeTable.COLUMN_IMAGE_URL, recipe.imageUrl.joinToString(","))
        values.put(RecipeDBHelper.RecipeTable.COLUMN_INGREDIENTS, recipe.ingredients.joinToString(","))
        values.put(RecipeDBHelper.RecipeTable.COLUMN_CUISINE, recipe.cuisine)

        return database?.insert(RecipeDBHelper.RecipeTable.TABLE_RECIPES, null, values) ?: -1
    }

    // Retrieve a list of all recipes from the database
    fun getAllRecipes(): ArrayList<Recipe> {
        val recipes = ArrayList<Recipe>()
        val query = "SELECT * FROM ${RecipeDBHelper.RecipeTable.TABLE_RECIPES}"
        val cursor: Cursor? = database?.rawQuery(query, null)

        cursor?.use { // Auto-closes the cursor when done
            val idIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_ID)
            val nameIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_NAME)
            val titleIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_TITLE)
            val descriptionIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_DESCRIPTION)
            val imageUrlIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_IMAGE_URL)
            val ingredientsIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_INGREDIENTS)
            val cuisineIndex = it.getColumnIndex(RecipeDBHelper.RecipeTable.COLUMN_CUISINE)

            while (it.moveToNext()) {
                val id = if (idIndex != -1) it.getInt(idIndex) else -1
                val name = if (nameIndex != -1) it.getString(nameIndex) else ""
                val title = if (titleIndex != -1) it.getString(titleIndex) else ""
                val description = if (descriptionIndex != -1) it.getString(descriptionIndex) else ""
                val imageUrl = if (imageUrlIndex != -1) it.getString(imageUrlIndex).split(",") else emptyList()
                val ingredients = if (ingredientsIndex != -1) it.getString(ingredientsIndex).split(",") else emptyList()
                val cuisine = if (cuisineIndex != -1) it.getString(cuisineIndex) else ""

                val recipe = Recipe(id, name, title, description, imageUrl, ingredients, cuisine)
                recipes.add(recipe)
            }
        }

        return recipes
    }

    // Update an existing recipe in the database
    fun updateRecipe(recipe: Recipe): Int {
        val values = ContentValues()
        values.put(RecipeDBHelper.RecipeTable.COLUMN_NAME, recipe.name)
        values.put(RecipeDBHelper.RecipeTable.COLUMN_TITLE, recipe.title)
        values.put(RecipeDBHelper.RecipeTable.COLUMN_DESCRIPTION, recipe.description)
        values.put(RecipeDBHelper.RecipeTable.COLUMN_IMAGE_URL, recipe.imageUrl.joinToString(","))
        values.put(RecipeDBHelper.RecipeTable.COLUMN_INGREDIENTS, recipe.ingredients.joinToString(","))
        values.put(RecipeDBHelper.RecipeTable.COLUMN_CUISINE, recipe.cuisine)

        return database?.update(
            RecipeDBHelper.RecipeTable.TABLE_RECIPES,
            values,
            "${RecipeDBHelper.RecipeTable.COLUMN_ID} = ?",
            arrayOf(recipe.id.toString())
        ) ?: -1
    }

    // Delete a recipe from the database
    fun deleteRecipe(recipe: Recipe): Int {
        return database?.delete(
            RecipeDBHelper.RecipeTable.TABLE_RECIPES,
            "${RecipeDBHelper.RecipeTable.COLUMN_ID} = ?",
            arrayOf(recipe.id.toString())
        ) ?: -1
    }
}

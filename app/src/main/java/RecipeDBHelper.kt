package com.example.recipebook3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "RecipeDatabase.db"
        const val DATABASE_VERSION = 1
    }

    // Define the table and column names
    object RecipeTable {
        const val TABLE_RECIPES = "recipes"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_INGREDIENTS = "ingredients"
        const val COLUMN_CUISINE = "cuisine"
    }

    // Create the table
    private val createTableQuery = """
        CREATE TABLE ${RecipeTable.TABLE_RECIPES} (
            ${RecipeTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${RecipeTable.COLUMN_NAME} TEXT,
            ${RecipeTable.COLUMN_TITLE} TEXT,
            ${RecipeTable.COLUMN_DESCRIPTION} TEXT,
            ${RecipeTable.COLUMN_IMAGE_URL} TEXT,
            ${RecipeTable.COLUMN_INGREDIENTS} TEXT,
            ${RecipeTable.COLUMN_CUISINE} TEXT
        )
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${RecipeTable.TABLE_RECIPES}")
        onCreate(db)
    }
}

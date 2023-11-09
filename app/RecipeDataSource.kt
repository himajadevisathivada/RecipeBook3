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
            Log.e(RecipeDataSource::class.java.name, "Error opening the database: $e")
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
        values.put(RecipeDBHelper.COLUMN_TITLE, recipe.title)
        values.put(RecipeDBHelper.COLUMN_DESCRIPTION, recipe.description)
        values.put(RecipeDBHelper.COLUMN_INGREDIENTS, recipe.ingredients)
        values.put(RecipeDBHelper.COLUMN_DIRECTIONS, recipe.directions)
        values put (RecipeDBHelper.COLUMN_CATEGORY, recipe.category)
        values.put(RecipeDBHelper.COLUMN_IMAGE_URL, recipe.imageUrl)

        return database?.insert(RecipeDBHelper.TABLE_RECIPES, null, values) ?: -1
    }

    // Retrieve a list of all recipes from the database
    fun getAllRecipes(): ArrayList<Recipe> {
        val recipes = ArrayList<Recipe>()
        val query = "SELECT * FROM ${RecipeDBHelper.TABLE_RECIPES}"
        val cursor: Cursor? = database?.rawQuery(query, null)

        cursor?.use { // Auto-closes the cursor when done
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndex(RecipeDBHelper.COLUMN_ID))
                val title = it.getString(it.getColumnIndex(RecipeDBHelper.COLUMN_TITLE))
                val description = it.getString(it.getColumnIndex

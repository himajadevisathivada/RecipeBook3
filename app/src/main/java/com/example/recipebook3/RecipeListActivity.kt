package com.example.recipebook3
import RecipeAdapter
import RecipeDetailActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeListActivity : AppCompatActivity() {
    private val recipes = mutableListOf<Recipe>()
    private val adapter = RecipeAdapter(recipes) { recipe -> showRecipeDetail(recipe) }
    private lateinit var recipeDataSource: RecipeDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        // Initialize RecipeDataSource and open the database
        recipeDataSource = RecipeDataSource(this)
        recipeDataSource.open()

        // Reference the RecyclerView and set up the adapter
        val recipeRecyclerView = findViewById<RecyclerView>(R.id.recipeRecyclerView)
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.adapter = adapter

        // Handle category filter button click
        val categoryFilterButton = findViewById<Button>(R.id.categoryFilterButton)
        categoryFilterButton.setOnClickListener { showCategories() }

        // Set an onClickListener for your "Add Recipe" button
        val addRecipeButton = findViewById<Button>(R.id.addRecipeButton)
        addRecipeButton.setOnClickListener { addRecipe() }
    }

    private fun showRecipeDetail(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        // Pass recipe details to the RecipeDetailActivity
        intent.putExtra("recipeId", recipe.id)
        startActivity(intent)
    }

    private fun showCategories() {
        val intent = Intent(this, RecipeCategoriesActivity::class.java)
        startActivity(intent)
    }

    private fun addRecipe() {
        val intent = Intent(this, AddRecipeActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        // Fetch data from the database and update the adapter
        val recipes = recipeDataSource.getAllRecipes()
        adapter.updateData(recipes)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Close the database when the activity is destroyed
        recipeDataSource.close()
    }
}

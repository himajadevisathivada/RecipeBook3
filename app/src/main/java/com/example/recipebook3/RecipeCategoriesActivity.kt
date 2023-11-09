package com.example.recipebook3

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class RecipeCategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_categories)

        // Initialize RecipeDataSource to perform database operations
        val dataSource = RecipeDataSource(this)
        dataSource.open()

        // Define recipe categories
        val categories = arrayOf("Appetizers", "Main Dishes", "Desserts", "Salads", "Soups", "Beverages", "Other")

        // Initialize categoryListView and set up the adapter
        val categoryListView: ListView = findViewById(R.id.categoryListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        categoryListView.adapter = adapter

        // Handle category item click
        categoryListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedCategory = categories[position]

            // Create an Intent to start RecipeListActivity and pass the selected category
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("selectedCategory", selectedCategory)
            startActivity(intent)
        }

        // Close the database when you're done
        dataSource.close()
    }
}

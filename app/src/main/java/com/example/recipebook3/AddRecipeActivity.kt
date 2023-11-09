package com.example.recipebook3

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.recipebook3.R

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var recipeNameEditText: EditText
    private lateinit var recipeDescriptionEditText: EditText
    private lateinit var ingredientsEditText: EditText
    private lateinit var stepsEditText: EditText
    private lateinit var cuisineSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        recipeNameEditText = findViewById(R.id.recipeNameEditText)
        recipeDescriptionEditText = findViewById(R.id.recipeDescriptionEditText)
        ingredientsEditText = findViewById(R.id.ingredientsEditText)
        stepsEditText = findViewById(R.id.stepsEditText)
        cuisineSpinner = findViewById(R.id.cuisineSpinner)

        // Initialize the Spinner with cuisine options
        val cuisines = arrayOf("Italian", "Indian", "Mexican", "Chinese", "Japanese", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuisines)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cuisineSpinner.adapter = adapter

        findViewById<Button>(R.id.addRecipeButton).setOnClickListener {
            // Retrieve user input from EditText fields and Spinner
            val recipeName = recipeNameEditText.text.toString().trim()
            val recipeDescription = recipeDescriptionEditText.text.toString().trim()
            val ingredients = ingredientsEditText.text.toString().trim()
            val steps = stepsEditText.text.toString().trim()
            val selectedCuisine = cuisineSpinner.selectedItem.toString()

            if (recipeName.isEmpty() || recipeDescription.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Save the recipe data
            }
        }
    }
}

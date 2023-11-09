import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.recipebook3.R
import com.example.recipebook3.Recipe
import com.example.recipebook3.RecipeDataSource

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Retrieve recipe details from the Intent
        val recipeName = intent.getStringExtra("recipeName")
        val recipeDescription = intent.getStringExtra("recipeDescription")

        if (recipeName != null) {
            supportActionBar?.title = recipeName

            // Display recipe details
            val recipeNameTextView = findViewById<TextView>(R.id.recipeNameTextView)
            val recipeDescriptionTextView = findViewById<TextView>(R.id.recipeDescriptionTextView)

            recipeNameTextView.text = recipeName
            recipeDescriptionTextView.text = recipeDescription

            // When the activity is created, it will display the recipe details.
        }
    }
}

package com.nafisa008.nafisa008recipe


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.ui.theme.Nafisa008RecipeTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nafisa008RecipeTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Nafisa’s Recipe App") }
                        )
                    }
                ) { innerPadding ->
                    // Show the screen you want to preview
                     SimpleHomeScreen(modifier = Modifier.padding(innerPadding))
                     //SimpleRecipeDetailScreen(modifier = Modifier.padding(innerPadding))
                     //SimpleHistoryScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Home Screen
@Composable
fun SimpleHomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Nafisa’s Recipe App", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        // View Recipes Option
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { /* Navigate to Recipe List */ }
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pasta),
                contentDescription = "View Recipes",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "View Recipes",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        // Add New Recipe Option
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { /* Navigate to Add Recipe */ }
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Add New Recipe",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "Add New Recipe",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        // Favorites / History Option
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { /* Navigate to History */ }
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.biriyani),
                contentDescription = "Favorites / History",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "Favorites / History",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// ------------------- Recipe Detail Screen -------------------
@Composable
fun SimpleRecipeDetailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text("Recipe Detail", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.pasta),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Ingredients & Steps:\n\n" +
                    "Step 1: Chop the vegetables\n" +
                    "Step 2: Boil water\n" +
                    "Step 3: Add pasta\n" +
                    "Step 4: Cook for 10 minutes\n" +
                    "Step 5: Drain and serve",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// ------------------- History / Favorites Screen -------------------
@Composable
fun SimpleHistoryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "History / Favorites",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))

        // --- Past Recipe 1 ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chicken),
                contentDescription = "Past Recipe for chicken",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // dark overlay for readable text
            )
            Text(
                text = "Chicken Curry",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(8.dp))

        // --- Past Recipe 2 ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.biriyani),
                contentDescription = "Past Recipe for biriyani",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Text(
                text = "Biriyani",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}



//@Composable
//fun TestLayout(modifier: Modifier = Modifier){
//    Row(modifier = Modifier.fillMaxSize()) {
//        Column (
//            modifier = Modifier.fillMaxHeight().width(100.dp).background(Color.Yellow),
//            verticalArrangement = Arrangement.SpaceEvenly
//        ){
//            repeat(6){
//                //display a dice
//                Image(
//                    painter = painterResource(image_ids[it]),
//                    contentDescription = "Dice $it"
//                )
//            }
//        }
//        Column(modifier = Modifier.fillMaxHeight().width(100.dp).background(Color.Gray),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(text = "Column 2")
//        }
//        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Green),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Column 3")
//        }
//    }
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Nafisa008RecipeTheme {
//        Greeting("Android")
//    }
//}
//
//private val image_ids = listOf (
    //R.drawable.home,
//    R.drawable.dice_2,
//    R.drawable.dice_3,
//    R.drawable.dice_4,
//    R.drawable.dice_5,
//    R.drawable.dice_6

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewHome() {
    SimpleHomeScreen()
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewDetail() {
    SimpleRecipeDetailScreen()
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewHistory() {
    SimpleHistoryScreen()
}

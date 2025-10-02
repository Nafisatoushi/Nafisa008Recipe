package com.nafisa008.nafisa008recipe

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nafisa008.nafisa008recipe.ui.theme.Nafisa008RecipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            Nafisa008RecipeTheme {
                //scaffold: layout component for material design layout
                // top app bar, bottom bar, drawer ...
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                      TestLayout(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TestLayout(modifier: Modifier = Modifier){
    Row(modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier.fillMaxHeight().width(100.dp).background(Color.Yellow),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            repeat(6){
                //display a dice
                Image(
                    painter = painterResource(image_ids[it]),
                    contentDescription = "Dice $it"
                )
            }
        }
        Column(modifier = Modifier.fillMaxHeight().width(100.dp).background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Column 2")
        }
        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Green),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Column 3")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
} 

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Nafisa008RecipeTheme {
        Greeting("Android")
    }
}

private val image_ids = listOf (
    R.drawable.dice_1,
    R.drawable.dice_2,
    R.drawable.dice_3,
    R.drawable.dice_4,
    R.drawable.dice_5,
    R.drawable.dice_6
)
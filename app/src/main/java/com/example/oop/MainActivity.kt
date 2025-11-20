package com.example.oop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oop.ui.theme.OOPTheme
import com.example.oop.ui.theme.keyword.Decide_reset
import com.example.oop.ui.theme.keyword.Keyword_color
import com.example.oop.ui.theme.keyword.Keyword_letter
import com.example.oop.ui.theme.keyword.Keyword_shape
import com.example.oop.ui.theme.keyword.Keyword_type
import com.example.oop.ui.theme.keyword.Search_select2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OOPTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Search_select2(
                        modifier = Modifier.padding(innerPadding)
                    )
                    Keyword_letter(
                        modifier = Modifier.padding(innerPadding)
                    )
                    Keyword_type(
                        modifier = Modifier.padding(innerPadding)
                    )
                    Keyword_shape(
                        modifier = Modifier.padding(innerPadding)
                    )
                    Keyword_color(
                        modifier = Modifier.padding(innerPadding)
                    )
                    Decide_reset(
                        modifier = Modifier.padding(innerPadding)
                    )
                    //Greeting(
                    //    name = "Android",
                    //   modifier = Modifier.padding(innerPadding)
                    //)
                }
            }
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
    OOPTheme {
        Greeting("Android")
    }
}
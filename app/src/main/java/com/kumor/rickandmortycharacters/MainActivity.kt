package com.kumor.rickandmortycharacters

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumor.rickandmortycharacters.repository.RickAndMortyResponse
import com.kumor.rickandmortycharacters.ui.theme.RickAndMortyCharactersTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData()

        setContent {
            RickAndMortyCharactersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Kafelek()
                    CharactersDisplay(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun CharactersDisplay(viewModel: MainViewModel){
    val characters by viewModel.liveDataCharacters.observeAsState(emptyList())

    if (characters.isNotEmpty()) {
        LazyColumn {
            items(characters) {character ->
                Log.d("MainActivity", "${character.name}")
                Kafelek(
                    name=character.name,
                    gender=character.gender,
                    status=character.status,
                    img=character.image
                )
            }
        }
    }
}

@Composable
fun Kafelek(name: String, gender: String, status: String, img: String) {
    var img = R.drawable.morty;

    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            focusedElevation = 6.dp,
        )
    ) {
        Row {
            Image(
                painter = painterResource(id = img),
                contentDescription = name,
                modifier = Modifier.height(100.dp)
            )
            Column {
                Text(
                    modifier = Modifier.padding(16.dp, 8.dp),
                    text = name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row {
                    Text(
                        modifier = Modifier.padding(16.dp, 8.dp),
                        text = gender,
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 8.dp),
                        text = status,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KafelekPreview() {
    RickAndMortyCharactersTheme {
        Kafelek("Morty", "Male", "Alive", "")
    }
}
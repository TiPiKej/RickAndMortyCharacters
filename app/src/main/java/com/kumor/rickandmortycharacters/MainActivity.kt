package com.kumor.rickandmortycharacters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.AsyncImage
import com.kumor.rickandmortycharacters.details.DetailsActivity
import com.kumor.rickandmortycharacters.repository.Character
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
                    MainView(
                        viewModel = viewModel,
                        onClick = {id -> navigateToDetailsActivity(id)}
                    )
                }
            }
        }
    }

    fun navigateToDetailsActivity(id: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("CUSTOM_KEY", id)
        startActivity(intent)
    }
}

@Composable
fun MainView(viewModel: MainViewModel, onClick: (Int) -> Unit){
    val uiState by viewModel.liveDataCharacters.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }
        uiState.error !== null -> {
            MyErrorView(uiState.error)
        }
        uiState.data !== null -> {
            uiState.data?.let { MylistView(
                characters = it,
                onClick={id -> onClick.invoke(id)}
            ) }
        }
    }
}

@Composable
fun MyLoadingView() {
    CircularProgressIndicator()
}

@Composable
fun MyErrorView(error: String?) {
    Text(text = error ?: "")
}

@Composable
fun MylistView(characters: List<Character>, onClick: (Int) -> Unit) {
    LazyColumn {
        items(characters) {character ->
            Log.d("MainActivity", "${character.name}")
            Kafelek(
                id=character.id,
                name=character.name,
                gender=character.gender,
                status=character.status,
                img=character.image,
                onClick={id -> onClick.invoke(id)}
            )
        }
    }
}

@Composable
fun Kafelek(id: Int, name: String, gender: String, status: String, img: String, onClick: (Int) -> Unit) {
    Card(
//        zakladam ze id mojego elementu / kafelka to pole name
        modifier = Modifier.padding(16.dp).fillMaxWidth().clickable { onClick.invoke(id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            focusedElevation = 6.dp,
        )
    ) {
        Row {
            AsyncImage(
                model = img,
                contentDescription = "Postać",
                placeholder = painterResource(id = R.drawable.morty)
            )
//            (
//                painter = painterResource(id = img),
//                contentDescription = name,
//                modifier = Modifier.height(100.dp)
//            )
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
        Kafelek(0, "Morty", "Male", "Alive", "", onClick = {})
    }
}
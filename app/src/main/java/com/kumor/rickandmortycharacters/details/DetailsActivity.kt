package com.kumor.rickandmortycharacters.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kumor.rickandmortycharacters.MainViewModel
import com.kumor.rickandmortycharacters.MyErrorView
import com.kumor.rickandmortycharacters.MyLoadingView
import com.kumor.rickandmortycharacters.R
import com.kumor.rickandmortycharacters.UiState
import com.kumor.rickandmortycharacters.repository.Character
import com.kumor.rickandmortycharacters.ui.theme.RickAndMortyCharactersTheme

class DetailsActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("CUSTOM_KEY", 0)

//        Toast.makeText(this, "details id: $id", Toast.LENGTH_SHORT).show()

        viewModel.getCharacterData(id)

        setContent {
            RickAndMortyCharactersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DeTailsView(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun DeTailsView(viewModel: MainViewModel) {
    val uiState by viewModel.liveDataCharacter.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }
        uiState.error !== null -> {
            MyErrorView(uiState.error)
        }
        uiState.data !== null -> {
            uiState.data?.let { Character(
                character = it
            ) }
        }
    }
}

@Composable
fun Character(character: Character) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp, 8.dp),
            text = character.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )

        Divider(
            color = Color.Black
        )

        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            placeholder = painterResource(id = R.drawable.morty),
            modifier = Modifier.fillMaxWidth()
        )

        Divider(
            color = Color.Black
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                focusedElevation = 6.dp,
            )
        ) {
            Row {
                Text(
                    text = "Gender:",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = character.gender,
                    modifier = Modifier.padding(16.dp, 8.dp),
                    fontSize = 22.sp,
                )
            }

            Row {
                Text(
                    text = "Status:",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = character.status,
                    modifier = Modifier.padding(16.dp, 8.dp),
                    fontSize = 22.sp,
                )
            }

            Row {
                Text(
                    text = "Location:",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = character.location.name,
                    modifier = Modifier.padding(16.dp, 8.dp),
                    fontSize = 22.sp,
                )
            }
        }
    }
}

package com.example.waterandplants

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
import com.example.waterandplants.ui.theme.WaterAndPlantsTheme
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.ImeAction



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterAndPlantsTheme {
                PlantScreen()
                }
            }
        }
    }


@Composable

fun PlantScreen(viewModel: PlantViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var inputName by remember { mutableStateOf("") }
    var inputDate by remember { mutableStateOf("") }
    var addStep by remember { mutableStateOf(0) } // 0 = name, 1 = date

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .systemBarsPadding()
    ) {
        Column {

            Text(
                text = "My Plants",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn() {
                items(uiState.plants) { plant ->
                    PlantItem(plant = plant)
                }
            }
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 25.dp)
        ) {
            if (addStep == 0) {

                TextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Enter plant name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (inputName.isNotBlank()) {
                                addStep = 1
                            }
                        }
                    )
                )
            } else {

                TextField(
                    value = inputDate,
                    onValueChange = { inputDate = it },
                    label = { Text("Enter last watering date") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (inputDate.isNotBlank()) {

                                val newPlant = PlantUi(
                                    plantName = inputName,
                                    plantImage = R.drawable.image1,
                                    waterDate = inputDate
                                )

                                viewModel.addPlant(newPlant)

                                // Reset
                                inputName = ""
                                inputDate = ""
                                addStep = 0
                            }
                        }
                    )
                )
            }
        }
    }
}


@Composable
fun PlantItem(plant: PlantUi){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id=plant.plantImage),
            contentDescription = plant.plantName,
            modifier = Modifier
                .size(90.dp)
                .padding(end=8.dp)
        )
        Column {
            Text(text = plant.plantName, style = MaterialTheme.typography.titleLarge)
            Text(text = "Last watered: ${plant.waterDate}",style = MaterialTheme.typography.bodyMedium)
        }

    }
}




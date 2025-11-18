package com.example.waterandplants

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlantViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(emptyList()))
    var uiState: StateFlow<UiState> = _uiState.asStateFlow()

     fun addPlant ( plant: PlantUi ){
        val currentState = _uiState.value
        val currentList: List<PlantUi> = currentState.plants
        val newList: List<PlantUi> = currentList + plant
        _uiState.value = currentState.copy(plants = newList)
    }

    fun deletePlant (plant: PlantUi){
        val currentState = _uiState.value
        val currentList: List<PlantUi> = currentState.plants
        val newList: List<PlantUi> = currentList - plant
        _uiState.value = currentState.copy(plants = newList)
    }

    fun changeWaterDate(plantToChange:PlantUi){
        val currentState=_uiState.value
        val newDate = "String"
        val newList: List<PlantUi> = currentState.plants.map { plant ->
            if (plant.plantName == plantToChange.plantName)
                plant.copy(waterDate = newDate)
            else
                plant
        }
        _uiState.value = currentState.copy(plants = newList)
    }

    fun changeImage(plantToChange: PlantUi){
        val currentState = _uiState.value
        @DrawableRes val newImage: Int = 0
        val newList: List<PlantUi> = currentState.plants.map { plant ->
            if (plant.plantName == plantToChange.plantName)
                plant.copy(plantImage = newImage)
            else
                plant
        }
        _uiState.value = currentState.copy(plants = newList)
    }
}
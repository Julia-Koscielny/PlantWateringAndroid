package com.example.waterandplants

import androidx.annotation.DrawableRes

data class PlantUi ( val plantName: String,
                     val waterDate: String,
                     @DrawableRes val plantImage: Int = R.drawable.image1){
}
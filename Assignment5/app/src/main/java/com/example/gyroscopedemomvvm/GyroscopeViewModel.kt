package com.example.gravitySensordemomvvm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlin.math.pow


class gravitySensorViewModel(private val repository: GravitySensorRepository) : ViewModel() {
    var newX = 0.0f;
    var newY = 0.0f;

    var velocityX = 0.0f;
    var velocityY = 0.0f;

    var maxWidth = 0.0f;
    var maxHeight = 0.0f;


    var currentTime = 0;


    val gravityReading = repository.getGravityFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GravityReading(0f, 0f, 0f, 0L, 0f, 0f)
        )

    fun calcPosition(gravityReading: GravityReading, currentTime: Long){
        if (currentTime == 0L){
            return
        }

        val dTime = (gravityReading.time - currentTime)/10.0.pow(12.5);

        velocityX +=  (dTime * 5 * gravityReading.x).toFloat();
        velocityY +=  (dTime * 5 * gravityReading.y).toFloat();
         newX +=  (dTime * 5 * -velocityX).toFloat();
         newY +=  (dTime * 5 * velocityY).toFloat();

        gravityReading.time = currentTime;

        if(0 > newX)
        {
            newX = 0.0f ;
            velocityX = 0.0f
        }
        if(0 > newY)
        {
            newY = 0.0f;
            velocityY = 0.0f
        }

        if(maxWidth -100 < newX)
        {
            newX = maxWidth - 100;
            velocityX = 0.0f
        }
        if(maxHeight - 100 < newY)
        {
            newY = maxHeight - 100
            velocityY = 0.0f
        }
        gravityReading.prevX = newX.toFloat();
        gravityReading.prevY = newY.toFloat();


    }

    fun wallValues(minX: Float, minY: Float, maxX: Float, maxY: Float, gravityReading: GravityReading){
        maxWidth = maxX
        maxHeight = maxY

    }
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as gravitySensorApp)
                gravitySensorViewModel(application.gravitySensorRepository)
            }
        }
    }
}

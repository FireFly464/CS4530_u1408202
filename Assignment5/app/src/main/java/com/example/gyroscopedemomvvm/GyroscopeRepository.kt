package com.example.gravitySensordemomvvm


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlin.math.pow

data class GravityReading(val x: Float,
                          val y: Float,
                          val z: Float,
                          var time: Long,
                          var prevX: Float,
                          var prevY: Float
                       )


//repository and model
class GravitySensorRepository(private val sensorManager: SensorManager) {




    fun getGravityFlow(): Flow<GravityReading> = channelFlow {
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        if (gravitySensor == null) {
            return@channelFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val gravReading = GravityReading(event.values[0],
                                                event.values[1],
                                                event.values[2],
                                                0L,
                                                0.0f,
                                                0.0f,
                                               )


                trySendBlocking(gravReading)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_UI)
        awaitClose { sensorManager.unregisterListener(listener) }
    }
}


package com.example.gravitySensordemomvvm

import android.app.Application
import android.content.Context
import android.hardware.SensorManager

class gravitySensorApp : Application() {

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val gravitySensorRepository by lazy {
        GravitySensorRepository(sensorManager)
    }
}

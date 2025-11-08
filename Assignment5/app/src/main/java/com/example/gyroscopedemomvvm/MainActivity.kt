package com.example.gyroscopedemomvvm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gravitySensordemomvvm.gravitySensorViewModel
import com.example.gravitySensordemomvvm.ui.theme.gravitySensorDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            gravitySensorDemoTheme {
                val myVM: gravitySensorViewModel = viewModel(factory = gravitySensorViewModel.Companion.Factory)
                GravitySensorScreen(myVM)
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GravitySensorScreen(viewModel: gravitySensorViewModel) {
    val gravityReading by viewModel.gravityReading.collectAsStateWithLifecycle()
    viewModel.calcPosition(gravityReading, System.currentTimeMillis())



    Surface(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Gravity readings:\nx = ${gravityReading.x}, y = ${gravityReading.y}, z = ${gravityReading.z}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        Text(
            text = "\n\n\nPositions:\nX Position = ${gravityReading.prevX}, Y Position = ${gravityReading.prevY}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
    }

    BoxWithConstraints (Modifier

        .fillMaxSize()
    ){
        viewModel.wallValues(minWidth.value, minHeight.value, maxWidth.value, maxHeight.value, gravityReading)

        Box(Modifier
            .offset(gravityReading.prevX.dp, gravityReading.prevY.dp)
            .size(100.dp)
            .background(Color.Blue, CircleShape))



    }

}

 package com.example.roomdemo

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.roomdemo.ui.theme.ComposeDEMOTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.SharingStarted
import com.example.roomdemo.room.funFactEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

 @Serializable
 data class FunFact (var text:String, var source_url:String?=null)


class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val client = HttpClient(Android)
    {
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    // Model (using Repository)
    val dao=(application as TodoApp).repository
    private val funListMutable = MutableStateFlow(listOf<String>())

    // Converting the Flow to StateFlow for UI Compose
    val funListReadOnly:Flow<List<funFactEntity?>> = dao.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Adds new task
    suspend fun addItem (item: String){
        try{
            val responseText: funFactEntity = client.get("https://uselessfacts.jsph.pl//api/v2/facts/random").body()
            funListMutable.value = funListMutable.value + responseText.text

            dao.addTask(responseText)

        }
        catch (e: Exception)
        {
            Log.e("FunFact Activity", "Error fetching", e)
        }
    }

}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDEMOTheme {
               val vm:MyViewModel = viewModel()
               TodoList (vm)
            }
        }
    }
}

@Composable
fun TodoList(myVM: MyViewModel) {
    Column(Modifier.fillMaxWidth().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        val observableList by myVM.funListReadOnly.collectAsState(emptyList())
        var itemText by remember { mutableStateOf("") }


        Row {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch()
                {
                    myVM.addItem(itemText)
                }
            }) {
                Text("Fetch Item")

            }

        }

        Spacer(Modifier.height(20.dp))
        Text("FunFact List", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = Color.Blue)
        Row{
            LazyColumn {
                items(observableList) { Text(it?.text?:"",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))}
            }
        }
    }
}


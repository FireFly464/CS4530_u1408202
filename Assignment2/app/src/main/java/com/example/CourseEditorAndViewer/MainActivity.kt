package com.example.CourseEditorAndViewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.CourseEditorAndViewer.ui.theme.ComposeDEMOTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.material3.Card
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CourseModel : ViewModel()
{
    //Create model
    private val courseMutable = MutableStateFlow(listOf<Course>())
    val courseReadOnly : MutableStateFlow<List<Course>> = courseMutable

    //Add course to the model
    fun addCourse (item: Course){
        courseMutable.value = courseMutable.value + item
    }

    //Delete course from the model
    fun deleteCourse (item: Course){
        courseMutable.value = courseMutable.value - item
    }
}

//Create and define a data class for the course
data class Course(val courseName: String,
                  val department: String,
                  val courseNum: String,
                  val loc: String)

//Main activity
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDEMOTheme {
               val vm:CourseModel = viewModel()
               TodoList (vm)
            }
        }
    }
}

//Function that controls what is held in the course
@ExperimentalAnimationApi
@Composable
fun courseItem(currentCourse: Course, courseVM: CourseModel)
{
    var expanded by rememberSaveable{
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.clickable(){
            expanded = !expanded
    })
    {
        Text(currentCourse.courseName,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold)

        AnimatedVisibility(visible = expanded){
            Column (verticalArrangement = Arrangement.SpaceEvenly)
            {

                //Displays the department, number, and location

                Text("Department: " + currentCourse.department)
                Text("Course Number: " + currentCourse.courseNum)
                Text("Location: " + currentCourse.loc)

                //When the button is clicked, delete the course.
                Button(onClick ={

                    courseVM.deleteCourse(currentCourse)

                })
                {
                    Text("Delete Course")
                }

            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TodoList(courseVM: CourseModel) {
    Column(Modifier.fillMaxWidth().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        val observableList by
        courseVM.courseReadOnly.collectAsState()
        var num by remember { mutableStateOf("") }
        var dept by remember { mutableStateOf("") }
        var loc by remember { mutableStateOf("") }


        //The text input for the department
        Row {
            OutlinedTextField(
                value = dept,
                onValueChange = { newInput2 -> dept = newInput2},
                label = { Text("Department") }
            )}

        //The text input for the course number
        Row {
            OutlinedTextField(
                value = num,
                onValueChange = { newInput1 -> num = newInput1 },
                label = { Text("Course Number") }
            )
        }

        //The text input for the location
        Row{
            OutlinedTextField(
                value = loc,
                onValueChange = { newInput3 -> loc = newInput3},
                label = { Text("Location") }
            )
        }

        //Button for adding the course to the list
        Row {
            Button(onClick = {
                //Sets the current course to the values in the text fields.
                //Pass the current course to the
                val currentCourse : Course = Course(dept + " " + num,
                                                    dept,
                                                    num,
                                                    loc);
                courseVM.addCourse(currentCourse)
                                num = ""
                                dept = ""
                                loc = ""
            })
            {
                Text("Add Course")
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("ToDo List", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = Color.Blue)
        Row{
            LazyColumn {
                items(observableList) {
                    courseItem(it, courseVM)
                }
            }
        }
    }
}


package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button1 = findViewById<Button>(R.id.button)
        buttonClick(button1, "Purple")

        val button2 = findViewById<Button>(R.id.button2)
        buttonClick(button2, "Red")


        val button3 = findViewById<Button>(R.id.button3)
        buttonClick(button3, "Yellow")



        val button4 = findViewById<Button>(R.id.button4)
        buttonClick(button4, "Green")

        //
        val button5 = findViewById<Button>(R.id.button5)
        buttonClick(button5, "Blue")

    }

    //The function that sends the color information to the next activity
    fun buttonClick(buttonNumber: Button, color: String) {
        buttonNumber.setOnClickListener {
            val buttonContent = color;
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("BUTTON_CONTENT", buttonContent)
            startActivity(intent)
        }
    }
}
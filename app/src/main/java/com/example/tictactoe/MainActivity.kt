 package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.ui.Number_Of_Rounds

 class MainActivity : AppCompatActivity() {

    private lateinit var player1: EditText
    private lateinit var player2: EditText
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        player1 = findViewById(R.id.et_player1_name)
        player2 = findViewById(R.id.et_player2_name)
        startButton = findViewById(R.id.btn_start_game)

        startButton.setOnClickListener {
        if(!player1.text.isEmpty() && !player2.text.isEmpty()) {
            Intent(this@MainActivity, Number_Of_Rounds::class.java).also {
                it.putExtra("player1", player1.text.toString())
                it.putExtra("player2", player2.text.toString())
                startActivity(it)
                finish()
            }
        }
            else if(player1.text.isEmpty()){
                Toast.makeText(this@MainActivity , "please enter the name of player 1", Toast.LENGTH_LONG).show()
            }
        else if(player2.text.isEmpty()){
            Toast.makeText(this@MainActivity , "please enter the name of player 2", Toast.LENGTH_LONG).show()
        }
            else{
            Toast.makeText(this@MainActivity , "please enter the name of player 1 & 2", Toast.LENGTH_SHORT).show()
        }
        }
    }
}
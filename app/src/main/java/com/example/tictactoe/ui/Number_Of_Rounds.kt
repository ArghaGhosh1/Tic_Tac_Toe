package com.example.tictactoe.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.R

class Number_Of_Rounds : AppCompatActivity() , View.OnClickListener
{

    private lateinit var player1: TextView
    private lateinit var player2: TextView

    private lateinit var round1: Button
    private lateinit var round3: Button
    private lateinit var round5: Button
    private lateinit var round7: Button
    private lateinit var round10: Button
    private lateinit var roundCustom: Button
    private var nummberOfRounds = 0
    private lateinit var startTour : Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_number_of_rounds)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player1 = findViewById(R.id.tv_player1_name_display)
        player2 = findViewById(R.id.tv_player2_name_display)
        round1 = findViewById(R.id.btn_1_round)
        round3 = findViewById(R.id.btn_3_rounds)
        round5 = findViewById(R.id.btn_5_rounds)
        round7 = findViewById(R.id.btn_7_rounds)
        round10 = findViewById(R.id.btn_10_rounds)
        roundCustom = findViewById(R.id.btn_custom_rounds)
        startTour = findViewById(R.id.btn_start_tournament)

        round1.setOnClickListener (this)
        round3.setOnClickListener (this)
        round5.setOnClickListener (this)
        round7.setOnClickListener (this)
        round10.setOnClickListener (this)
        roundCustom.setOnClickListener (this)

        val showPlayer1 = intent.getStringExtra("player1")
        val showPlayer2 = intent.getStringExtra("player2")

        player1.text = showPlayer1
        player2.text = showPlayer2

        startTour.setOnClickListener()
        {
            if (nummberOfRounds == 0) {
                Toast.makeText(
                    this@Number_Of_Rounds,
                    " select the number of rounds",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Intent(this@Number_Of_Rounds, PlayGround::class.java).also {
                    it.putExtra("player1",showPlayer1)
                    it.putExtra("player2", showPlayer2)
                    it.putExtra("rounds", nummberOfRounds)
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun resetRound(){
        val rounds = mutableListOf<Button>()

        rounds.add(round1)
        rounds.add(round3)
        rounds.add(round5)
        rounds.add(round7)
        rounds.add(round10)
        rounds.add(roundCustom)

        for(round in rounds){
            round.setTextColor(Color.BLACK)
            round.setTypeface(round.typeface, Typeface.BOLD)
            round.background = ContextCompat.getDrawable(this, R.drawable.round_selected_button)
        }
    }

    private fun selectedRound(button: Button){
        resetRound()
            button.setTextColor(Color.GRAY)
        button.setTypeface(button.typeface, Typeface.BOLD)
            button.background = ContextCompat.getDrawable(this, R.drawable.selected_rounds)


    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_1_round -> { selectedRound(round1)
            nummberOfRounds = 1 }
            R.id.btn_3_rounds -> { selectedRound(round3)
            nummberOfRounds = 3 }
            R.id.btn_5_rounds -> { selectedRound(round5)
            nummberOfRounds = 5 }
            R.id.btn_7_rounds -> { selectedRound(round7)
            nummberOfRounds = 7 }
            R.id.btn_10_rounds -> { selectedRound(round10)
            nummberOfRounds = 10 }
            R.id.btn_custom_rounds -> { selectedRound(roundCustom)
                Toast.makeText(this@Number_Of_Rounds,"this feature is unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }




}
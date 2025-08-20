package com.example.tictactoe.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.MainActivity
import com.example.tictactoe.R

class ResultActivity : AppCompatActivity()
{

    private lateinit var player1View : TextView
    private lateinit var player2View : TextView
    private lateinit var player1ScoreView: TextView
    private lateinit var player2ScoreView: TextView
    private lateinit var tournament_summary: TextView
    private lateinit var tournament_winner: TextView
    private lateinit var new_tournament_btn: Button
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        player1View = findViewById(R.id.player1_name)
        player2View = findViewById(R.id.player2_name)
        player1ScoreView = findViewById(R.id.player1_final_score)
        player2ScoreView = findViewById(R.id.player2_final_score)
        tournament_summary = findViewById(R.id.tournament_summary)
        tournament_winner = findViewById(R.id.tournament_winner)
        new_tournament_btn = findViewById(R.id.new_tournament_btn)

        val player1 = intent.getStringExtra("player1")
        val player2 = intent.getStringExtra("player2")
        val totalRounds = intent.getIntExtra("totalRounds",0)
        val player1Score = intent.getIntExtra("player1Score",0)
        val player2Score = intent.getIntExtra("player2Score",0)

        player1View.text = player1
        player2View.text = player2

        player1ScoreView.text = player1Score.toString()
        player2ScoreView.text = player2Score.toString()
        tournament_summary.text = "Best of $totalRounds Tournament"

        if(player1Score>player2Score)
        {
            tournament_winner.text = "$player1 Wins Tournament!"
        }
        else if(player1Score<player2Score) {
            tournament_winner.text = "$player2 Wins Tournament!"
        }
        else{
            tournament_winner.text = "Match Draw"

        }

        new_tournament_btn.setOnClickListener {
            Intent(this@ResultActivity, MainActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}
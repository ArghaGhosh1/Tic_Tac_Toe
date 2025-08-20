package com.example.tictactoe.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.R

class PlayGround : AppCompatActivity() {

    private lateinit var numberOfround: TextView

    private lateinit var player1WinStatus: TextView
    private lateinit var player2WinStatus: TextView
    private var gameBoard = Array(3) { Array(3) { "" } }
    private lateinit var whoesMove: TextView
    private lateinit var player1Extra: Button
    private lateinit var player2Extra: Button
    private var currentPlayer = "X"
    private lateinit var viewPLayer1: TextView
    private lateinit var viewPalyer2: TextView
    private lateinit var streak1: TextView
    private lateinit var streak2: TextView
    private var player1Wins = 0
    private var player2Wins = 0
    private var player1Streak = 0
    private var player2Streak = 0
    private var player1ExtraMoves = 0
    private var player2ExtraMoves = 0
    private var currentRound = 1
    private lateinit var currentRoundTextView: TextView
    private lateinit var nextRound: Button

    private lateinit var cell00: Button
    private lateinit var cell01: Button
    private lateinit var cell02: Button
    private lateinit var cell10: Button
    private lateinit var cell11: Button
    private lateinit var cell12: Button
    private lateinit var cell20: Button
    private lateinit var cell21: Button
    private lateinit var cell22: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play_ground)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        numberOfround = findViewById(R.id.tv_total_rounds)
        viewPLayer1 = findViewById(R.id.tv_player1_name)
        viewPalyer2 = findViewById(R.id.tv_player2_name)
        player1WinStatus = findViewById(R.id.tv_player1_score)
        player2WinStatus = findViewById(R.id.tv_player2_score)
        currentRoundTextView = findViewById(R.id.tv_current_round)
        nextRound = findViewById(R.id.btn_next_round)
        whoesMove = findViewById(R.id.tv_current_player)
        streak1 = findViewById(R.id.tv_player1_streak)
        streak2 = findViewById(R.id.tv_player2_streak)
        player1Extra = findViewById(R.id.tv_player1_extra_moves)
        player2Extra = findViewById(R.id.tv_player2_extra_moves)

        cell00 = findViewById(R.id.btn_cell_0_0)
        cell01 = findViewById(R.id.btn_cell_0_1)
        cell02 = findViewById(R.id.btn_cell_0_2)
        cell10 = findViewById(R.id.btn_cell_1_0)
        cell11 = findViewById(R.id.btn_cell_1_1)
        cell12 = findViewById(R.id.btn_cell_1_2)
        cell20 = findViewById(R.id.btn_cell_2_0)
        cell21 = findViewById(R.id.btn_cell_2_1)
        cell22 = findViewById(R.id.btn_cell_2_2)


        var totalRounds = intent.getIntExtra("rounds", 0)
        val player1 = intent.getStringExtra("player1")
        val player2 = intent.getStringExtra("player2")



        numberOfround.text = "of $totalRounds"
        viewPLayer1.text = player1
        viewPalyer2.text = player2
        whoesMove.text = player1

        fun makeMove(row: Int, col: Int, button: Button, player: String, clicked: String) {

            if (clicked == "click") {
                if (gameBoard[row][col].isEmpty()) {

                    // Update game array and UI
                    gameBoard[row][col] = player
                    button.text = player

                    // Set colors
                    if (player == "X") {
                        button.setTextColor(ContextCompat.getColor(this, R.color.player_x_color))
                    } else {
                        button.setTextColor(ContextCompat.getColor(this, R.color.player_o_color))
                    }

                    button.isEnabled = false

                    // CHECK GAME STATE HERE!
                    checkGameState()

                }
            }
            // Check if the cell is empty
            if (gameBoard[row][col].isEmpty()) {

                // Update game array and UI
                gameBoard[row][col] = currentPlayer
                button.text = currentPlayer


                // Set colors
                if (currentPlayer == "X") {
                    button.setTextColor(ContextCompat.getColor(this, R.color.player_x_color))
                    whoseMove()
                } else {
                    button.setTextColor(ContextCompat.getColor(this, R.color.player_o_color))
                    whoseMove()
                }

                button.isEnabled = false

                // CHECK GAME STATE HERE!
                checkGameState()

                // Switch player (only if game hasn't ended)
                val winner = checkWin()
                if (winner == null && !checkDraw()) {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                }

            }
        }


        fun nextRound() {
            currentRound++
            currentRoundTextView.text = "Round $currentRound"
            if (currentRound > totalRounds) {
                Intent(this@PlayGround, ResultActivity::class.java).also {
                    it.putExtra("player1", player1)
                    it.putExtra("player2", player2)
                    it.putExtra("player1Score", player1Wins)
                    it.putExtra("player2Score", player2Wins)
                    it.putExtra("totalRounds", totalRounds)
                    startActivity(it)
                }
            }

        }
        cell00.setOnClickListener {
            makeMove(0, 0, cell00, "hi", "na")
        }
        cell01.setOnClickListener {
            makeMove(0, 1, cell01, "hi", "na")
        }
        cell02.setOnClickListener {
            makeMove(0, 2, cell02, "hi", "na")
        }
        cell10.setOnClickListener {
            makeMove(1, 0, cell10, "hi", "na")
        }
        cell11.setOnClickListener {
            makeMove(1, 1, cell11, "hi", "na")
        }
        cell12.setOnClickListener {
            makeMove(1, 2, cell12, "hi", "na")
        }
        cell20.setOnClickListener {
            makeMove(2, 0, cell20, "hi", "na")
        }
        cell21.setOnClickListener {
            makeMove(2, 1, cell21, "hi", "na")
        }
        cell22.setOnClickListener {
            makeMove(2, 2, cell22, "hi", "na")
        }
        nextRound.setOnClickListener {
            resetBoard()
            nextRound()
        }

        fun backToBasicsX() {

            cell00.setOnClickListener {
                makeMove(0, 0, cell00, "hi", "na")
            }
            cell01.setOnClickListener {
                makeMove(0, 1, cell01, "hi", "na")
            }
            cell02.setOnClickListener {
                makeMove(0, 2, cell02, "hi", "na")
            }
            cell10.setOnClickListener {
                makeMove(1, 0, cell10, "hi", "na")
            }
            cell11.setOnClickListener {
                makeMove(1, 1, cell11, "hi", "na")
            }
            cell12.setOnClickListener {
                makeMove(1, 2, cell12, "hi", "na")
            }
            cell20.setOnClickListener {
                makeMove(2, 0, cell20, "hi", "na")
            }
            cell21.setOnClickListener {
                makeMove(2, 1, cell21, "hi", "na")
            }
            cell22.setOnClickListener {
                makeMove(2, 2, cell22, "hi", "na")
            }
            nextRound.setOnClickListener {
                resetBoard()
                nextRound()
            }
        }

        fun backToBasicsO() {

            cell00.setOnClickListener {
                makeMove(0, 0, cell00, "hi", "na")
            }
            cell01.setOnClickListener {
                makeMove(0, 1, cell01, "hi", "na")
            }
            cell02.setOnClickListener {
                makeMove(0, 2, cell02, "hi", "na")
            }
            cell10.setOnClickListener {
                makeMove(1, 0, cell10, "hi", "na")
            }
            cell11.setOnClickListener {
                makeMove(1, 1, cell11, "hi", "na")
            }
            cell12.setOnClickListener {
                makeMove(1, 2, cell12, "hi", "na")
            }
            cell20.setOnClickListener {
                makeMove(2, 0, cell20, "hi", "na")
            }
            cell21.setOnClickListener {
                makeMove(2, 1, cell21, "hi", "na")
            }
            cell22.setOnClickListener {
                makeMove(2, 2, cell22, "hi", "na")
            }
            nextRound.setOnClickListener {
                resetBoard()
                nextRound()
            }
        }



        player2Extra.setOnClickListener {

            if (player2ExtraMoves == 0) {
                Toast.makeText(
                    this@PlayGround,
                    "You don't have any extra moves",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (player2ExtraMoves > 0)
            {
                if (whoesMove.text == player1) {
                    whoesMove.text = player2
                    val player1Type = "O"
                    whoesMove.setTextColor(ContextCompat.getColor(this, R.color.player_o_color))

                    cell00.setOnClickListener {
                        makeMove(0, 0, cell00, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell01.setOnClickListener {
                        makeMove(0, 1, cell01, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell02.setOnClickListener {
                        makeMove(0, 2, cell02, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell10.setOnClickListener {
                        makeMove(1, 0, cell10, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell11.setOnClickListener {
                        makeMove(1, 1, cell11, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell12.setOnClickListener {
                        makeMove(1, 2, cell12, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell20.setOnClickListener {
                        makeMove(2, 0, cell20, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell21.setOnClickListener {
                        makeMove(2, 1, cell21, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    cell22.setOnClickListener {
                        makeMove(2, 2, cell22, player1Type, "click")
                        backToBasicsO()
                        resetRound()
                    }
                    player2ExtraMoves--
                    player2Extra.text = "Extra Moves: $player2ExtraMoves"
                }
                selectedRound(player2Extra)
            }

        }

        player1Extra.setOnClickListener {

            if (player1ExtraMoves == 0) {
                Toast.makeText(
                    this@PlayGround,
                    "You don't have any extra moves",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (player1ExtraMoves > 0)
            {

                if (whoesMove.text == player2) {
                    whoesMove.text = player1
                    val player2Type = "X"
                    whoesMove.setTextColor(ContextCompat.getColor(this, R.color.player_x_color))

                    cell00.setOnClickListener {
                        makeMove(0, 0, cell00, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell01.setOnClickListener {
                        makeMove(0, 1, cell01, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell02.setOnClickListener {
                        makeMove(0, 2, cell02, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell10.setOnClickListener {
                        makeMove(1, 0, cell10, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell11.setOnClickListener {
                        makeMove(1, 1, cell11, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell12.setOnClickListener {
                        makeMove(1, 2, cell12, player2Type, "click")
                        backToBasicsX()
                        resetRound()

                    }
                    cell20.setOnClickListener {
                        makeMove(2, 0, cell20, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell21.setOnClickListener {
                        makeMove(2, 1, cell21, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    cell22.setOnClickListener {
                        makeMove(2, 2, cell22, player2Type, "click")
                        backToBasicsX()
                        resetRound()
                    }
                    player1ExtraMoves--
                    player1Extra.text = "Extra Moves: $player1ExtraMoves"
                }
                selectedRound(player1Extra)
            }
        }

        //END
    }


    private fun checkGameState() {
        val winner = checkWin()
        if (winner != null) {
            handleWin(winner)
        } else if (checkDraw()) {
        }
    }

    private fun checkWin(): String? {

        for (i in 0..2) {
            if (gameBoard[i][0].isNotEmpty() && gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2]) {
                return gameBoard[i][0]
            }
        }
        for (j in 0..2) {
            if (gameBoard[0][j].isNotEmpty() && gameBoard[0][j] == gameBoard[1][j] && gameBoard[1][j] == gameBoard[2][j]) {
                return gameBoard[0][j]
            }
        }
        if (gameBoard[0][0].isNotEmpty() && gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]) {
            return gameBoard[0][0]
        }
        if (gameBoard[0][2].isNotEmpty() && gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]) {
            return gameBoard[0][2]
        }
        return null
    }

    private fun checkDraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoard[i][j].isEmpty()) {
                    return false
                }
            }
        }
        return true


    }

    private fun handleWin(Winner: String) {
        if (Winner == "X") {
            player1Wins++
            player1Streak++
            streak(Winner)
            player1SuperPower()
            player1WinStatus.text = "Wins: $player1Wins"
            streak1.text = "Streak: $player1Streak"
            buttonDisable()
        } else {
            player2Wins++
            player2Streak++
            streak(Winner)
            player2SuperPower()
            player2WinStatus.text = "Wins: $player2Wins"
            streak2.text = "Streak: $player2Streak"
            buttonDisable()
        }
    }


    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                gameBoard[i][j] = ""
            }
        }
        val buttons = mutableListOf<Button>()

        buttons.add(cell00)
        buttons.add(cell01)
        buttons.add(cell02)
        buttons.add(cell10)
        buttons.add(cell11)
        buttons.add(cell12)
        buttons.add(cell20)
        buttons.add(cell21)
        buttons.add(cell22)

        for (button in buttons) {
            button.text = null
            button.isEnabled = true
            button.setTextColor(ContextCompat.getColor(this, R.color.text_primary))

        }
    }

    private fun buttonDisable() {
        val buttons = mutableListOf<Button>()

        buttons.add(cell00)
        buttons.add(cell01)
        buttons.add(cell02)
        buttons.add(cell10)
        buttons.add(cell11)
        buttons.add(cell12)
        buttons.add(cell20)
        buttons.add(cell21)
        buttons.add(cell22)

        for (button in buttons) {
            button.isEnabled = false
        }
    }

    private fun streak(Winner: String) {
        if (Winner == "O") {
            player1Streak = 0
            streak1.text = "Streak: $player1Streak"
        } else {
            player2Streak = 0
            streak2.text = "Streak: $player2Streak"
        }
    }

    private fun player1SuperPower() {
        if (player1Streak % 3 == 0) {
            player1ExtraMoves++
            player1Extra.text = "Extra Moves: $player1ExtraMoves"
        }
    }

    private fun player2SuperPower() {
        if (player2Streak % 3 == 0) {
            player2ExtraMoves++
            player2Extra.text = "Extra Moves: $player2ExtraMoves"
        }
    }

    fun whoseMove(): String? {
        val player1 = intent.getStringExtra("player1")
        val player2 = intent.getStringExtra("player2")



        if (currentPlayer == "X") {
            whoesMove.text = player2
            whoesMove.setTextColor(ContextCompat.getColor(this, R.color.player_o_color))
            return "X"
        } else {
            whoesMove.text = player1
            whoesMove.setTextColor(ContextCompat.getColor(this, R.color.player_x_color))
            return "O"
        }
    }

    fun resetRound() {
        val rounds = mutableListOf<Button>()

        rounds.add(player1Extra)
        rounds.add(player2Extra)

        for (round in rounds) {
            round.setTextColor(Color.BLACK)
            round.setTypeface(round.typeface, Typeface.BOLD)
            round.background = ContextCompat.getDrawable(this, R.color.round_button)
        }
    }

    fun selectedRound(button: Button) {
        resetRound()
        button.setTextColor(Color.GRAY)
        button.setTypeface(button.typeface, Typeface.BOLD)
        button.background = ContextCompat.getDrawable(this, R.drawable.selected_rounds)


    }
}



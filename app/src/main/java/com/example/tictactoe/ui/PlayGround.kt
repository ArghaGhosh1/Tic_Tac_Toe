package com.example.tictactoe.ui

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
import kotlin.math.round

class PlayGround : AppCompatActivity()  {

    private lateinit var numberOfround : TextView
    private var gameBoard = Array(3) { Array(3) { "" } }

    private  var currentPlayer = "X"
    private lateinit var viewPLayer1 : TextView
    private lateinit var viewPalyer2 : TextView
    private var player1Wins = 0
    private var player2Wins = 0
    private var player1Streak = 0
    private var player2Streak = 0
    private var player1ExtraMoves = 0
    private var player2ExtraMoves = 0
    private var currentRound = 1
    private var totalRounds = 5  // Get this from previous activity
    private var isUsingExtraMove = true
    private lateinit var nextRound: Button

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
        nextRound = findViewById(R.id.btn_next_round)

        val cell00 = findViewById<Button>(R.id.btn_cell_0_0)
        val cell01 = findViewById<Button>(R.id.btn_cell_0_1)
        val cell02 = findViewById<Button>(R.id.btn_cell_0_2)
        val cell10 = findViewById<Button>(R.id.btn_cell_1_0)
        val cell11 = findViewById<Button>(R.id.btn_cell_1_1)
        val cell12 = findViewById<Button>(R.id.btn_cell_1_2)
        val cell20 = findViewById<Button>(R.id.btn_cell_2_0)
        val cell21 = findViewById<Button>(R.id.btn_cell_2_1)
        val cell22 = findViewById<Button>(R.id.btn_cell_2_2)
        val extraMove = findViewById<Button>(R.id.btn_use_extra_move)

        val round = intent.getIntExtra("rounds",0)
        val player1 = intent.getStringExtra("player1")
        val player2 = intent.getStringExtra("player2")

        numberOfround.text = "of $round"
        viewPLayer1.text = player1
        viewPalyer2.text = player2

        fun makeMove(row: Int, col: Int, button: Button) {
            // Check if the cell is empty
            if (gameBoard[row][col].isEmpty()) {

                // Update game array and UI
                gameBoard[row][col] = currentPlayer
                button.text = currentPlayer

                // Set colors
                if (currentPlayer == "X") {
                    button.setTextColor(ContextCompat.getColor(this, R.color.player_x_color))
                } else {
                    button.setTextColor(ContextCompat.getColor(this, R.color.player_o_color))
                }

                button.isEnabled = false

                // CHECK GAME STATE HERE!
                checkGameState()

                // Switch player (only if game hasn't ended)
                val winner = checkWin()
                if (winner == null && !checkDraw()) {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    updateTurnIndicator()
                }
            }
        }


        cell00.setOnClickListener {
            makeMove(0, 0, cell00)
        }
        cell01.setOnClickListener {
            makeMove(0, 1, cell01)
        }
        cell02.setOnClickListener {
            makeMove(0, 2, cell02)
        }
        cell10.setOnClickListener {
            makeMove(1, 0, cell10)
        }
        cell11.setOnClickListener {
            makeMove(1, 1, cell11)
        }
        cell12.setOnClickListener {
            makeMove(1, 2, cell12)
        }
        cell20.setOnClickListener {
            makeMove(2, 0, cell20)
        }
        cell21.setOnClickListener {
            makeMove(2, 1, cell21)
        }
        cell22.setOnClickListener {
            makeMove(2 ,2, cell22)
        }
        nextRound.setOnClickListener { nextRound() }
        extraMove.setOnClickListener {  }


    }

    private fun checkGameState() {
        val winner = checkWin()

        if (winner != null) {
            // Someone won!
            handleWin(winner)
        } else if (checkDraw()) {
            // It's a draw!
            handleDraw()
        }
        // If neither win nor draw, game continues normally
    }

    private fun checkWin(): String? {
        // Check all possible win conditions

        // Check rows (horizontal)
        for (i in 0..2) {
            if (gameBoard[i][0].isNotEmpty() &&
                gameBoard[i][0] == gameBoard[i][1] &&
                gameBoard[i][1] == gameBoard[i][2]) {
                return gameBoard[i][0]  // Returns "X" or "O"
            }
        }

        // Check columns (vertical)
        for (j in 0..2) {
            if (gameBoard[0][j].isNotEmpty() &&
                gameBoard[0][j] == gameBoard[1][j] &&
                gameBoard[1][j] == gameBoard[2][j]) {
                return gameBoard[0][j]  // Returns "X" or "O"
            }
        }

        // Check main diagonal (top-left to bottom-right)
        if (gameBoard[0][0].isNotEmpty() &&
            gameBoard[0][0] == gameBoard[1][1] &&
            gameBoard[1][1] == gameBoard[2][2]) {
            return gameBoard[0][0]  // Returns "X" or "O"
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (gameBoard[0][2].isNotEmpty() &&
            gameBoard[0][2] == gameBoard[1][1] &&
            gameBoard[1][1] == gameBoard[2][0]) {
            return gameBoard[0][2]  // Returns "X" or "O"
        }

        // No winner found
        return null
    }

    private fun checkDraw(): Boolean {
        // Check if all cells are filled
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoard[i][j].isEmpty()) {
                    return false  // Found an empty cell, not a draw
                }
            }
        }
        return true  // All cells filled and no winner = draw
    }

    private fun handleWin(winner: String) {
        // Update status message
        val statusText = findViewById<TextView>(R.id.tv_game_status)
        val playerName = if (winner == "X") "Player 1" else "Player 2"
        statusText.text = "🎉 $playerName ($winner) Wins! 🎉"

        // Disable all remaining buttons
        disableAllButtons()

        // Update scores
        updatePlayerScore(winner)

        // Show next round button
        findViewById<Button>(R.id.btn_next_round).visibility = View.VISIBLE

        // Hide extra move button
        findViewById<Button>(R.id.btn_use_extra_move).visibility = View.GONE
    }

    private fun handleDraw() {
        // Update status message
        val statusText = findViewById<TextView>(R.id.tv_game_status)
        statusText.text = "🤝 It's a Draw! 🤝"

        // Disable all buttons (they should already be disabled)
        disableAllButtons()

        // Show next round button
        findViewById<Button>(R.id.btn_next_round).visibility = View.VISIBLE

        // Hide extra move button
        findViewById<Button>(R.id.btn_use_extra_move).visibility = View.GONE

        // No score update for draw
    }

    private fun disableAllButtons() {
        val buttonIds = arrayOf(
            R.id.btn_cell_0_0, R.id.btn_cell_0_1, R.id.btn_cell_0_2,
            R.id.btn_cell_1_0, R.id.btn_cell_1_1, R.id.btn_cell_1_2,
            R.id.btn_cell_2_0, R.id.btn_cell_2_1, R.id.btn_cell_2_2
        )

        for (id in buttonIds) {
            findViewById<Button>(id).isEnabled = false
        }
    }

    private fun updatePlayerScore(winner: String) {
        if (winner == "X") {
            // Player 1 wins
            player1Wins++
            player1Streak++
            player2Streak = 0  // Reset opponent's streak

            // Update UI
            findViewById<TextView>(R.id.tv_player1_score).text = "Wins: $player1Wins"
            findViewById<TextView>(R.id.tv_player1_streak).text = "Streak: $player1Streak"
            findViewById<TextView>(R.id.tv_player2_streak).text = "Streak: $player2Streak"

            // Check for super power
            checkSuperPower(1, player1Streak)

        } else {
            // Player 2 wins
            player2Wins++
            player2Streak++
            player1Streak = 0  // Reset opponent's streak

            // Update UI
            findViewById<TextView>(R.id.tv_player2_score).text = "Wins: $player2Wins"
            findViewById<TextView>(R.id.tv_player2_streak).text = "Streak: $player2Streak"
            findViewById<TextView>(R.id.tv_player1_streak).text = "Streak: $player1Streak"

            // Check for super power
            checkSuperPower(2, player2Streak)
        }
    }

    private fun checkSuperPower(player: Int, streak: Int) {
        if (streak == 3) {
            // Award extra move
            if (player == 1) {
                player1ExtraMoves++
                showSuperPowerMessage("Player 1")
            } else {
                player2ExtraMoves++
                showSuperPowerMessage("Player 2")
            }

            // Update extra moves display
            updateExtraMovesDisplay()
        }
    }

    private fun showSuperPowerMessage(playerName: String) {
        val statusText = findViewById<TextView>(R.id.tv_game_status)
        statusText.text = "⚡ $playerName earned an EXTRA MOVE! ⚡"

        // Optional: Show a toast message
        Toast.makeText(this, "$playerName unlocked super power!", Toast.LENGTH_LONG).show()
    }

    private fun updateExtraMovesDisplay() {
        val extraMovesText = findViewById<TextView>(R.id.tv_extra_moves_count)
        val totalExtraMoves = player1ExtraMoves + player2ExtraMoves
        extraMovesText.text = "Available: $totalExtraMoves"
    }

    private fun updateTurnIndicator() {
        val currentTurnText = findViewById<TextView>(R.id.tv_current_player)

        // Get player names (you'll need to store these from previous activities)
        val player1Name = "Player 1"  // Replace with actual player 1 name
        val player2Name = "Player 2"  // Replace with actual player 2 name

        // Update the text based on current player
        if (currentPlayer == "X") {
            currentTurnText.text = "$player1Name (X)"
            currentTurnText.setTextColor(ContextCompat.getColor(this, R.color.player_x_color))
        } else {
            currentTurnText.text = "$player2Name (O)"
            currentTurnText.setTextColor(ContextCompat.getColor(this, R.color.player_o_color))
        }
    }



    private fun nextRound() {
        // Increment round counter
        currentRound++

        // Check if tournament is finished
        if (currentRound > totalRounds) {
            // Tournament finished - show final results
            showFinalResults()
            return
        }

        // Reset the game board for next round
        resetGameBoard()

        // Update round display
        updateRoundDisplay()

        // Reset current player to X (Player 1 always starts)
        currentPlayer = "X"

        // Update turn indicator
        updateTurnIndicator()

        // Hide next round button
        findViewById<Button>(R.id.btn_next_round).visibility = View.GONE

        // Show extra move button again (if applicable)
        findViewById<Button>(R.id.btn_use_extra_move).visibility = View.VISIBLE

        // Update status message
        findViewById<TextView>(R.id.tv_game_status).text = "Round $currentRound started! Make your move!"
    }

    private fun resetGameBoard() {
        // Clear the game board array
        for (i in 0..2) {
            for (j in 0..2) {
                gameBoard[i][j] = ""
            }
        }

        // Reset all button states
        val buttonIds = arrayOf(
            R.id.btn_cell_0_0, R.id.btn_cell_0_1, R.id.btn_cell_0_2,
            R.id.btn_cell_1_0, R.id.btn_cell_1_1, R.id.btn_cell_1_2,
            R.id.btn_cell_2_0, R.id.btn_cell_2_1, R.id.btn_cell_2_2
        )

        for (id in buttonIds) {
            val button = findViewById<Button>(id)
            button.text = ""  // Clear text
            button.isEnabled = true  // Make clickable again
            button.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        }

        // Reset extra move status
        isUsingExtraMove = false
    }

    private fun updateRoundDisplay() {
        // Update round counter in header
        findViewById<TextView>(R.id.tv_current_round).text = "Round $currentRound"
        findViewById<TextView>(R.id.tv_total_rounds).text = "of $totalRounds"
    }

    private fun showFinalResults() {
        val statusText = findViewById<TextView>(R.id.tv_game_status)
        val player1Name = viewPLayer1
        val player2Name = viewPalyer2

        // Determine tournament winner
        when {
            player1Wins > player2Wins -> {
                statusText.text = "🏆 $player1Name WINS THE TOURNAMENT! 🏆\n($player1Wins - $player2Wins)"
            }
            player2Wins > player1Wins -> {
                statusText.text = "🏆 $player2Name WINS THE TOURNAMENT! 🏆\n($player2Wins - $player1Wins)"
            }
            else -> {
                statusText.text = "🤝 TOURNAMENT TIED! 🤝\n($player1Wins - $player2Wins)"
            }
        }

        // Hide next round button
        findViewById<Button>(R.id.btn_next_round).visibility = View.GONE

        // Show play again or back to menu options
        // You can add these buttons or redirect to main menu
    }




}
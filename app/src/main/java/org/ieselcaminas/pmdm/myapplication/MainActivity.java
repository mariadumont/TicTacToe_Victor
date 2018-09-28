package org.ieselcaminas.pmdm.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    class Player {
        public String name;
        public String symbol;

        public Player(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    enum StateOfGame {
        Playing, Draw, Winner;
    }

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    public static final int NUM_ROWS = 3;
    private Button[][] buttons;
    private StateOfGame stateOfGame;
    private int numberOfMoves;




    private void initGame() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(NUM_ROWS);
        gridLayout.setRowCount(NUM_ROWS);

        addButtons(gridLayout);
        numberOfMoves = 0;
        stateOfGame = stateOfGame.Playing;
        currentPlayer = player1;
        displayTurn();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //comunicacion entre ACTIVITIES
        Intent intent=getIntent();

        player1 = new Player(intent.getExtras().getString("player1"), "X");
        player2 = new Player(intent.getExtras().getString("player2"), "O");



        setActionToResetButton();
        initGame();
    }

    private void setActionToResetButton() {
        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridLayout gridLayout = findViewById(R.id.gridLayout);
                gridLayout.removeAllViews();

                initGame();

            }
        });
    }


    private void addButtons(GridLayout gridLayout) {

        buttons = new Button[NUM_ROWS][NUM_ROWS];

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_ROWS; col++) {
                buttons[row][col] = new Button(this, null, android.R.attr.buttonStyleSmall);
                gridLayout.addView(buttons[row][col]);
                addListenerToButton(buttons[row][col]); //afegir el listener
            }
        }
    }

    private void addListenerToButton(Button btn) { //afegir el listener al boto
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(stateOfGame == StateOfGame.Playing)) {
                    return;
                }
                Button button = (Button) v;

                if (button.getText().equals("")) {

                    button.setText(currentPlayer.symbol);
                    numberOfMoves++;

                    stateOfGame = checkWinner();
                    if (stateOfGame != StateOfGame.Playing) {
                        displayEndOfGame();
                    } else {
                        changePlayer();
                        displayTurn();
                    }

                }


            }
        });
    }

    private void displayEndOfGame() {
        TextView textView = findViewById(R.id.textTurn);
        if (stateOfGame == StateOfGame.Draw) {
            textView.setText("DRAW");
        } else {
            if (stateOfGame == StateOfGame.Winner) {
                textView.setText("Winner: " + currentPlayer.name + " (Symbol: " + currentPlayer.symbol + ")");
            }
        }
    }

    private StateOfGame checkWinner() {
        boolean winner = checkRowsWinner();
        if (!winner) {
            winner = checkColsWinner();
        }
        if (!winner) {
            winner = checkDiagonalLefToRight();
        }
        if (!winner) {
            winner = checkDiagonalRightToLeft();
        }
        if (!winner) {
            if (numberOfMoves == 9) {
                return StateOfGame.Draw;
            }
        }
        if (winner) {
            return StateOfGame.Winner;
        } else {
            return StateOfGame.Playing;
        }

    }

    private boolean checkDiagonalRightToLeft() {
        int countSymbols = 0;
        for (int i = 0; i < NUM_ROWS; i++) {
            if (buttons[i][NUM_ROWS - i - 1].getText().equals(currentPlayer.symbol)) {
                countSymbols++;
            }

            if (countSymbols == NUM_ROWS) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalLefToRight() {
        int countSymbols = 0;
        for (int i = 0; i < NUM_ROWS; i++) {
            if (buttons[i][i].getText().equals(currentPlayer.symbol)) {
                countSymbols++;
            }

            if (countSymbols == NUM_ROWS) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColsWinner() {
        int countSymbols;
        for (int col = 0; col < NUM_ROWS; col++) {
            countSymbols = 0;
            for (int row = 0; row < NUM_ROWS; row++) {
                if (buttons[row][col].getText().equals(currentPlayer.symbol)) {
                    countSymbols++;
                }
            }
            if (countSymbols == NUM_ROWS) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRowsWinner() {
        int countSymbols;
        for (int row = 0; row < NUM_ROWS; row++) {
            countSymbols = 0;
            for (int col = 0; col < NUM_ROWS; col++) {
                if (buttons[row][col].getText().equals(currentPlayer.symbol)) {
                    countSymbols++;
                }
            }
            if (countSymbols == NUM_ROWS) {
                return true;
            }
        }
        return false;
    }


    private void displayTurn() {
        TextView textView = findViewById(R.id.textTurn);
        textView.setText("Turn player " + currentPlayer.name
                + " Symbol: "
                + currentPlayer.symbol);
    }

    private void changePlayer() {
        if (currentPlayer == player1) { // no hace falta .equals porque compara punteros, no contenido...
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}

package org.ieselcaminas.pmdm.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Button buttonOk = findViewById(R.id.buttonOkPlayers);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextPlayer1 = findViewById(R.id.editTextPlayer1);
                EditText editTextPlayer2 = findViewById(R.id.editTextPlayer2);

                //comunicacion entre ACTIVITIES
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("player1", editTextPlayer1.getText().toString());
                intent.putExtra("player2", editTextPlayer2.getText().toString());

                startActivity(intent);
            }


        });


    }
}

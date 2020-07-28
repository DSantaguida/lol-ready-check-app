package com.example.leaguereadycheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String ADDRESS = "com.example.leaguereadycheck.ADDRESS";

    Button searchButton;
    EditText summonerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("address", 0);

        if (pref.getString("address", null) == null) {
            searchButton = findViewById(R.id.searchButton);
            summonerLabel = findViewById(R.id.summonerName);

            searchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("address", summonerLabel.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, ReadyScreen.class);
                    intent.putExtra(ADDRESS, summonerLabel.getText().toString());
                    startActivity(intent);
                }
            });
        }
        else {
            Intent intent = new Intent(MainActivity.this, ReadyScreen.class);
            intent.putExtra(ADDRESS, pref.getString("address", null));
            startActivity(intent);

        }
    }

}
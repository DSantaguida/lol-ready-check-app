package com.example.leaguereadycheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leaguereadycheck.utilities.Request;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class ReadyScreen extends AppCompatActivity {

    TextView textView;
    Button acceptButton;
    Button declineButton;
    String address;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_screen);

        Intent intent = getIntent();
        address = intent.getStringExtra(MainActivity.ADDRESS);

        textView = findViewById(R.id.textView2);
        acceptButton = findViewById(R.id.acceptButton);
        declineButton = findViewById(R.id.declineButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Request().post(address + "/ready-check/accept");
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Request().post(address + "/ready-check/decline");
            }
        });

        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {

                    response = new Request().get(address + "/ready-check/status"); //Get ready check status
                    try {
                        sleep(500);
                        JSONObject json = new JSONObject(response);

                        if (Double.parseDouble(json.getString("timer")) > 0.0) { //Queue popped
                            runOnUiThread(queuePopped());
                        } else if (Double.parseDouble(json.getString("timer")) == 0.0)
                            runOnUiThread(inQueue());

                    } catch (JSONException e) {
                        runOnUiThread(noQueue());
                    } catch (InterruptedException e) {
                        textView.setText("Sleep failed");
                    }
                }
            }
        };

        final Thread myThread = new Thread(myRunnable);
        myThread.start();

    }

    private Runnable queuePopped() {
        return new Runnable(){
            @Override
            public void run() {
                textView.setText("Queue popped!");
                acceptButton.setVisibility(View.VISIBLE);
                declineButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private Runnable inQueue(){
        return new Runnable(){
            @Override
            public void run() {
                textView.setText("In queue...");
                acceptButton.setVisibility(View.INVISIBLE);
                declineButton.setVisibility(View.INVISIBLE);
            }
        };
    }

    private Runnable noQueue() {
        return new Runnable() {
            @Override
            public void run() {
                textView.setText("Not in a matchmaking queue");
                acceptButton.setVisibility(View.INVISIBLE);
                declineButton.setVisibility(View.INVISIBLE);
            }
        };
    }
}
package com.example.guessinggame;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guessinggame.databinding.ActivityMainBinding;


import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private int range = 100;
    private TextView lblRange;
    private Button guessBtn;
    private TextView lblOutput;
    private int theNumber;
    private int count;
//    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public void checkGuess() {
        String guessText = txtGuess.getText().toString();
        String message = "";
        try {
            count = count - 1;
            int guess = Integer.parseInt(guessText);
            if (guess < theNumber)
                message = guess + " is too low. Try again.";
            else if (guess > theNumber)
                message = guess + " is too high. Try again. " + count  + " tries left";
            else {
                message = "You win after " + count + " tries";
                newGame();
            }
            if (count < 1) {
                message = "You lose." + " guess number is " + theNumber + ". try now";
                newGame();
            }
        }catch (Exception e){
            message = "Enter a number between 1 to " + range;
        } finally {
            lblOutput.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }


    public void newGame(){
        theNumber = (int) (Math.random()*range+1);
        String textTextView2 = "Enter a number between 1 to " + range;
        String firstTextGuess = "" + range/2;
        lblRange.setText(textTextView2);
        txtGuess.setText(firstTextGuess);
        txtGuess.requestFocus();
        txtGuess.selectAll();
        count = 10;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        txtGuess = (EditText) findViewById(R.id.textGuess);
        guessBtn = (Button) findViewById(R.id.guessBtn);
        lblOutput = (TextView) findViewById(R.id.lblOutput);
        lblRange = (TextView) findViewById(R.id.textView2);
        newGame();
        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the Range:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item) {
                            case 0:
                                range = 10;
                                storeRange(10);
                                break;
                            case 1:
                                range = 100;
                                storeRange(100);
                                break;
                            case 2:
                                range = 1000;
                                storeRange(1000);
                                break;

                        }
                        newGame();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c) Kasimov N.S.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;
            case R.id.action_gamestats:
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void storeRange(int newRange){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }
}
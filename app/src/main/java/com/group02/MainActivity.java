package com.group02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnQuit;
    CheckBox ckbHorse1, ckbHorse2, ckbHorse3;
    SeekBar skbHorse1, skbHorse2, skbHorse3;
    TextView txtPoint;
    TextView txtBet1;
    TextView txtBet2;
    TextView txtBet3;

    int tongtien;

    int betHorse1, betHorse2, betHorse3;

    int indexWiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        ckbHorse1 = (CheckBox) findViewById(R.id.ckbHorse1);
        ckbHorse2 = (CheckBox) findViewById(R.id.ckbHorse2);
        ckbHorse3 = (CheckBox) findViewById(R.id.ckbHorse3);
        skbHorse1 = (SeekBar) findViewById(R.id.skbHorse1);
        skbHorse2 = (SeekBar) findViewById(R.id.skbHorse2);
        skbHorse3 = (SeekBar) findViewById(R.id.skbHorse3);
        txtPoint = (TextView) findViewById(R.id.txtPoint);
        txtBet1 = (TextView) findViewById(R.id.txtBet1);
        txtBet2 = (TextView) findViewById(R.id.txtBet2);
        txtBet3 = (TextView) findViewById(R.id.txtBet3);
        tongtien = Integer.parseInt(txtPoint.getText().toString());
        skbHorse1.setEnabled(false);
        skbHorse2.setEnabled(false);
        skbHorse3.setEnabled(false);
        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        final MediaPlayer sui = MediaPlayer.create(this, R.raw.sui);
        final MediaPlayer quit = MediaPlayer.create(this, R.raw.quit);
        final MediaPlayer background = MediaPlayer.create(this, R.raw.background_music);

        background.start();
        background.setLooping(true);

        skbHorse1.setMax(1000);
        skbHorse2.setMax(1000);
        skbHorse3.setMax(1000);
        CountDownTimer countDownTimer = new CountDownTimer(60000, 300) {
            @Override
            public void onTick(long millisUntilFinished) {

                int number = 100;
                Random random = new Random();
                int one = 0;
                int two = 0;
                int three = 0;
                do {
                    one = random.nextInt(number);
                    two = random.nextInt(number);
                    three = random.nextInt(number);
                } while (one == two || two == three || three == one);

                if (skbHorse1.getProgress() >= skbHorse1.getMax()) {
                    this.cancel();
                    sui.start();
                    openDialog("Horse 1 is a winner");
                    tongtien += betHorse1 * 2;
                    txtPoint.setText(tongtien + "");
                    btnStart.setText("RESTART");
                    return;
                }
                if (skbHorse2.getProgress() >= skbHorse2.getMax()) {
                    this.cancel();
                    sui.start();
                    openDialog("Horse 2 is a winner");
                    tongtien += betHorse2 * 2;
                    txtPoint.setText(tongtien + "");
                    btnStart.setText("RESTART");
                    return;
                }
                if (skbHorse3.getProgress() >= skbHorse3.getMax()) {
                    this.cancel();
                    sui.start();
                    openDialog("Horse 3 is a winner");
                    tongtien += betHorse3 * 2;
                    txtPoint.setText(tongtien + "");
                    btnStart.setText("RESTART");
                    return;
                }
                skbHorse1.setProgress(skbHorse1.getProgress() + one);
                skbHorse2.setProgress(skbHorse2.getProgress() + two);
                skbHorse3.setProgress(skbHorse3.getProgress() + three);
            }

            @Override
            public void onFinish() {

            }
        };


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click.start();

                Button tmp = (Button) v;
                if (tmp.getText().toString().equalsIgnoreCase("start")) {
                    if (betHorse1 == 0 && betHorse2 == 0 && betHorse3 == 0) {
                        openDialog("Please make your bet before start");
                    }
                    else {
                        skbHorse1.setProgress(0);
                        skbHorse2.setProgress(0);
                        skbHorse3.setProgress(0);
                        countDownTimer.start();
                        ckbHorse1.setEnabled(false);
                        ckbHorse2.setEnabled(false);
                        ckbHorse3.setEnabled(false);
                        tmp.setText("PAUSE");
                    }
                }
                else if (tmp.getText().toString().equalsIgnoreCase("pause")) {
                    countDownTimer.cancel();
                    tmp.setText("CONTINUE");
                }
                else if (tmp.getText().toString().equalsIgnoreCase("continue")) {
                    countDownTimer.start();
                    tmp.setText("PAUSE");
                }
                else if (tmp.getText().toString().equalsIgnoreCase("restart")) {

                    skbHorse1.setProgress(0);
                    skbHorse2.setProgress(0);
                    skbHorse3.setProgress(0);
                    betHorse1 = 0;
                    betHorse3 = 0;
                    betHorse2 = 0;
                    txtBet1.setText("Horse 1: " + betHorse1 + "$");
                    txtBet2.setText("Horse 2: " + betHorse2 + "$");
                    txtBet3.setText("Horse 3: " + betHorse3 + "$");
                    ckbHorse1.setEnabled(true);
                    ckbHorse2.setEnabled(true);
                    ckbHorse3.setEnabled(true);
                    ckbHorse1.setChecked(false);
                    ckbHorse2.setChecked(false);
                    ckbHorse3.setChecked(false);
                    tmp.setText("START");
                }
            }
        });
        ckbHorse1.setOnClickListener(cbOnclick);
        ckbHorse2.setOnClickListener(cbOnclick);
        ckbHorse3.setOnClickListener(cbOnclick);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit.start();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure want to quit?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
            }
        });
    }

    public void openDialog(String resultRace) {
        MessageDialog messageDialog = new MessageDialog();
        messageDialog.setResult(resultRace);
        messageDialog.show(getSupportFragmentManager(), "example");
    }
    private View.OnClickListener cbOnclick = v -> {

        MediaPlayer click = MediaPlayer.create(MainActivity.this, R.raw.click);

        click.start();

        CheckBox t = (CheckBox) v;
        if (t.isChecked()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (t.getId() == R.id.ckbHorse1)  builder.setTitle("Bet for horse 1, your total: " + tongtien + "$");
            if (t.getId() == R.id.ckbHorse2)  builder.setTitle("Bet for horse 2, your total: " + tongtien + "$");
            if (t.getId() == R.id.ckbHorse3)  builder.setTitle("Bet for horse 3, your total: " + tongtien + "$");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //betHorse1 = Integer.parseInt(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    t.setChecked(false);
                    dialog.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int amount = Integer.parseInt(input.getText().toString());
                        if (amount == 0) {
                            input.setError("More than zero please -.-");
                        }
                        else if (amount > tongtien) {
                            input.setError("Invalid bet amount");
                        } else {
                            tongtien -= amount;
                            txtPoint.setText(tongtien + "");
                            if (t.getId() == R.id.ckbHorse1) betHorse1 = amount;
                            if (t.getId() == R.id.ckbHorse2) betHorse2 = amount;
                            if (t.getId() == R.id.ckbHorse3) betHorse3 = amount;
                            txtBet1.setText("Horse 1: " + betHorse1 + "$");
                            txtBet2.setText("Horse 2: " + betHorse2 + "$");
                            txtBet3.setText("Horse 3: " + betHorse3 + "$");
                            dialog.dismiss();
                        }
                    } catch (NumberFormatException ex) {
                        input.setError("Please input number");
                    }
                }
            });
        }
        else {
            if (t.getId() == R.id.ckbHorse1)  {
                tongtien += betHorse1;
                betHorse1 = 0;
            }
            if (t.getId() == R.id.ckbHorse2)  {
                tongtien += betHorse2;
                betHorse2 = 0;
            } if (t.getId() == R.id.ckbHorse3)  {
                tongtien += betHorse3;
                betHorse3 = 0;
            }
            txtPoint.setText(tongtien + "");
            txtBet1.setText("Horse 1: " + betHorse1 + "$");
            txtBet2.setText("Horse 2: " + betHorse2 + "$");
            txtBet3.setText("Horse 3: " + betHorse3 + "$");
        }

    };
}
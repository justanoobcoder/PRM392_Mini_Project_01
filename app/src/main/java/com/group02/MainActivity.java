package com.group02;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnQuit, btnBet;
    CheckBox ckbHorse1, ckbHorse2, ckbHorse3;
    SeekBar skbHorse1, skbHorse2, skbHorse3;
    TextView txtPoint;
    EditText txtBet;
    int tiencuoc;
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
        txtBet = (EditText) findViewById(R.id.txtBet);
        btnBet = (Button) findViewById(R.id.btnBet);
        tongtien = Integer.parseInt(txtPoint.getText().toString());

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
                    openDialog("Horse 1 is a winner");
                    tongtien += betHorse1 * 2;
                    txtPoint.setText(tongtien + "");
                    betHorse1 = 0;
                    betHorse2 = 0;
                    betHorse3 = 0;
                    return;
                }
                if (skbHorse2.getProgress() >= skbHorse2.getMax()) {
                    this.cancel();
                    openDialog("Horse 2 is a winner");
                    tongtien += betHorse2 * 2;
                    txtPoint.setText(tongtien + "");
                    betHorse1 = 0;
                    betHorse2 = 0;
                    betHorse3 = 0;
                    return;
                }
                if (skbHorse3.getProgress() >= skbHorse3.getMax()) {
                    this.cancel();
                    openDialog("Horse 3 is a winner");
                    tongtien += betHorse3 * 2;
                    txtPoint.setText(tongtien + "");
                    betHorse1 = 0;
                    betHorse2 = 0;
                    betHorse3 = 0;
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
                skbHorse1.setProgress(0);
                skbHorse2.setProgress(0);
                skbHorse3.setProgress(0);
                countDownTimer.start();
                ckbHorse1.setChecked(false);
                ckbHorse2.setChecked(false);
                ckbHorse3.setChecked(false);
                txtBet.setText("");
            }
        });

        btnBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtBet.getText().length() == 0) {
                    txtBet.setError("The bet number can not be empty!");
                }else if (Integer.parseInt(txtBet.getText().toString()) > tongtien) {
                    txtBet.setError("The bet number can not be bigger than total point!");
                }else{
                    if (ckbHorse1.isChecked()) {
                        betHorse1 = Integer.parseInt(txtBet.getText().toString());
                        tongtien -= betHorse1;
                        txtPoint.setText(tongtien + "");
                    } else if (ckbHorse2.isChecked()) {
                        betHorse2 = Integer.parseInt(txtBet.getText().toString());
                        tongtien -= betHorse2;
                        txtPoint.setText(tongtien + "");
                    } else if (ckbHorse3.isChecked()) {
                        betHorse3 = Integer.parseInt(txtBet.getText().toString());
                        tongtien -= betHorse3;
                        txtPoint.setText(tongtien + "");
                    }else{
                        txtBet.setError("Please choose horse to bet!");
                    }
                }

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    public void openDialog(String resultRace) {
        MessageDialog messageDialog = new MessageDialog();
        messageDialog.setResult(resultRace);
        messageDialog.show(getSupportFragmentManager(), "example");
    }

}
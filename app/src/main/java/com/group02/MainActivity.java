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

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnStart,btnQuit,btnBet;
    CheckBox ckbHorse1,ckbHorse2,ckbHorse3;
    SeekBar skbHorse1,skbHorse2,skbHorse3;
    TextView txtPoint;
    EditText txtBet;
    int tiencuoc;
    int tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=(Button) findViewById(R.id.btnStart);
        btnQuit=(Button) findViewById(R.id.btnQuit);
        ckbHorse1=(CheckBox) findViewById(R.id.ckbHorse1);
        ckbHorse2=(CheckBox) findViewById(R.id.ckbHorse2);
        ckbHorse3=(CheckBox) findViewById(R.id.ckbHorse3);
        skbHorse1=(SeekBar) findViewById(R.id.skbHorse1);
        skbHorse2=(SeekBar) findViewById(R.id.skbHorse2);
        skbHorse3=(SeekBar) findViewById(R.id.skbHorse3);
        txtPoint = (TextView) findViewById(R.id.txtPoint);
        txtBet = (EditText) findViewById(R.id.txtBet);
        btnBet=(Button) findViewById(R.id.btnBet);
        tongtien =Integer.parseInt(txtPoint.getText().toString());
        CountDownTimer countDownTimer = new CountDownTimer(60000,300) {
            @Override
            public void onTick(long millisUntilFinished) {
                int number=10;
                Random random = new Random();
                int one= random.nextInt(number);
                int two = random.nextInt(number);
                int three = random.nextInt(number);
                if(skbHorse1.getProgress()>=skbHorse1.getMax()){
                    this.cancel();
                    openDialog("Horse 1 is a winner");
                }
                if(skbHorse2.getProgress()>=skbHorse2.getMax()){
                    this.cancel();
                    openDialog("Horse 2 is a winner");
                }
                if(skbHorse3.getProgress()>=skbHorse3.getMax()){
                    this.cancel();
                    openDialog("Horse 3 is a winner");
                }
                skbHorse1.setProgress(skbHorse1.getProgress()+one);
                skbHorse2.setProgress(skbHorse2.getProgress()+two);
                skbHorse3.setProgress(skbHorse3.getProgress()+three);

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
            }
        });
    }
    public void openDialog(String resultRace){
        MessageDialog messageDialog = new MessageDialog();
        messageDialog.setResult(resultRace);
        messageDialog.show(getSupportFragmentManager(),"example");
    }
}
package com.example.shuyang.protimerandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private class TimerThread implements Runnable{
        public void run(){
            loop();
        }
    }

    private int hours;
    private int minutes;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        int arr[] = intent.getIntArrayExtra(MainActivity.EXTRA_MESSAGE);
        String str = this.setupFromArray(arr);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextSize(64);
        textView.setText(str);

        TimerThread timerThread = new TimerThread();
        new Thread(timerThread).start();
    }

    private void loop(){
        long prevTimeMilli = System.currentTimeMillis();
        while(!this.checkFinish()){
            long currTimeMilli = System.currentTimeMillis();
            if(currTimeMilli - prevTimeMilli >= 1000){
                prevTimeMilli = currTimeMilli;
                this.shiftSecond();
            }
        }
        runOnUiThread(new Runnable(){
            public void run(){
                alertEnd();
            }
        });

    }

    private void alertEnd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Timer ended");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();

                //Return to main activity
                Intent intent = new Intent(DisplayMessageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void shiftSecond(){
        if(this.seconds == 0){
            this.seconds = 59;
            this.shiftMinutes();
        }
        else{
            --this.seconds;
        }

        this.refreshText();
    }

    private void shiftMinutes(){
        if(this.minutes == 0){
            this.minutes = 59;
            this.shiftHours();
        }
        else{
            --this.minutes;
        }
    }

    private void shiftHours(){
        --this.hours;
    }

    private void refreshText(){
        runOnUiThread(new Runnable(){
            public void run(){
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(getString());
            }
        });
    }

    private boolean checkFinish(){
        return seconds == 0 && minutes == 0 &&  hours == 0;
    }

    private String setupFromArray(int[] arr){
        hours = arr[0];
        minutes = arr[1];
        seconds = arr[2];

        return this.getString();
    }

    private String getString(){
        String textString = "";
        if(len(hours) == 1){
            textString += "0"+hours+" : ";
        }
        else if(hours == 0){
            textString += "00 : ";
        }
        else{
            textString += hours + " : ";
        }

        //Adding dummy zeroes for minutes
        if(len(minutes) == 1){
            textString += "0"+minutes+ " : ";
        }
        else if(minutes == 0){
            textString += "00 : ";
        }
        else{
            textString += minutes + " : ";
        }

        if(len(seconds) == 1){
            textString += "0"+seconds;
        }
        else if(seconds == 0){
            textString += "00";
        }
        else{
            textString += seconds;
        }
        return textString;
    }

    private int len(int n){
        return (int)(Math.log10(n)+1);
    }
}

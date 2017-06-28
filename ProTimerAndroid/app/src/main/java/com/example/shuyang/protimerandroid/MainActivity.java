package com.example.shuyang.protimerandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        if(editText.getText().toString().isEmpty() &&
                editText2.getText().toString().isEmpty() &&
                editText3.getText().toString().isEmpty()){
            this.noTimeAlert();
        }
        else if(!checkStringValidity(editText.getText().toString()) ||
            !checkStringValidity(editText2.getText().toString()) ||
            !checkStringValidity(editText3.getText().toString())){
            this.illegalStringAlert();
        }
        else if(!editText.getText().toString().isEmpty() && len(Integer.parseInt(editText.getText().toString())) > 2){
            this.badHourAlert();
        }
        else{
            int hours, minutes, seconds;
            if(!editText.getText().toString().isEmpty()){
                hours = Integer.parseInt(editText.getText().toString());
            }
            else{
                hours = 0;
            }

            if(!editText2.getText().toString().isEmpty()){
                minutes = Integer.parseInt(editText2.getText().toString());
            }
            else{
                minutes = 0;
            }

            if(!editText3.getText().toString().isEmpty()){
                seconds = Integer.parseInt(editText3.getText().toString());
            }
            else{
                seconds = 0;
            }

            int inps[] = new int[3];
            inps[0] = hours;
            inps[1] = minutes;
            inps[2] = seconds;

            intent.putExtra(EXTRA_MESSAGE, inps);
            startActivity(intent);
        }
    }

    private int len(int n){
        return (int)(Math.log10(n)+1);
    }

    private boolean checkStringValidity(String s){
        for(int i = 0; i < s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private void noTimeAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter a time");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
    }

    private void badHourAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter a number of hours less than 100");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
    }

    private void illegalStringAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter a number of hours less than 100");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
    }
}

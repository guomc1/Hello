package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    EditText editText1,editText2,editText3;
    double dollar,euro,won;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent = getIntent();
        dollar = intent.getDoubleExtra("dollar",0.0);
        euro = intent.getDoubleExtra("euro",0.0);
        won = intent.getDoubleExtra("won",0.0);

        editText1 = findViewById(R.id.editText5);
        editText2 = findViewById(R.id.editText6);
        editText3 = findViewById(R.id.editText7);

        editText1.setText("dollar rate =" + dollar);
        editText2.setText("euro rate =" + euro);
        editText3.setText("won rate =" + won);
    }

    public void save(View v){
        String s1 = editText1.getText().toString();
        String s2 = editText2.getText().toString();
        String s3 = editText3.getText().toString();

        try{
            dollar = Double.parseDouble(s1.split("=")[1]);
            euro = Double.parseDouble(s2.split("=")[1]);
            won = Double.parseDouble(s3.split("=")[1]);
        }catch (Exception e){
            Toast.makeText(this,"输入的格式不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("dollar",dollar);
        intent.putExtra("euro",euro);
        intent.putExtra("won",won);
        startActivity(intent);
    }
}

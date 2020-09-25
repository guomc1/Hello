package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    double dollar,euro,won;
    @Override
    protected void onCreate(    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


//        editText = findViewById(R.id.editText);
//        textView2 = findViewById(R.id.textView2);

//        textViewA = findViewById(R.id.textView19);
//        textViewB = findViewById(R.id.textView17);

        editText = findViewById(R.id.editText10);
        textView = findViewById(R.id.textView5);

        Intent intent = getIntent();

        dollar = intent.getDoubleExtra("dollar",0.146869);
        euro = intent.getDoubleExtra("euro",0.125881);
        won = intent.getDoubleExtra("won",171.924559);

    }

//    public void change(View v){
//        String str = editText.getText().toString();
//        Double n = 0.0;
//        if(str.isEmpty() || str.length() == 0){
//            textView2.setText("输入格式为以C（摄氏度）或F（华氏度）结尾");
//        }
//        else if(str.charAt(str.length()-1) == 'C'){
//            try {
//                n = Double.parseDouble(str.substring(0, str.length() - 1));
//            }catch (Exception e){
//                textView2.setText("请输入正确的温度");
//                return;
//            }
//            textView2.setText(String.format("%.2f",n * 1.8 + 32) + "F");
//        }
//        else if(str.charAt(str.length()-1) == 'F'){
//            try {
//                n = Double.parseDouble(str.substring(0, str.length() - 1));
//            }catch (Exception e){
//                textView2.setText("请输入正确的温度");
//                return;
//            }
//            textView2.setText(String.format("%.2f",(n - 32) / 1.8) + "C");
//        }
//        else{
//            textView2.setText("输入格式为以C（摄氏度）或F（华氏度）结尾");
//        }
//    }

//    public void addA1(View v){
//        textViewA.setText(Integer.parseInt(textViewA.getText().toString()) + 1 + "");
//    }
//    public void addA2(View v){
//        textViewA.setText(Integer.parseInt(textViewA.getText().toString()) + 2 + "");
//    }
//    public void addA3(View v){
//        textViewA.setText(Integer.parseInt(textViewA.getText().toString()) + 3 + "");
//    }
//    public void addB1(View v){
//        textViewB.setText(Integer.parseInt(textViewB.getText().toString()) + 1 + "");
//    }
//    public void addB2(View v){
//        textViewB.setText(Integer.parseInt(textViewB.getText().toString()) + 2 + "");
//    }
//    public void addB3(View v){
//        textViewB.setText(Integer.parseInt(textViewB.getText().toString()) + 3 + "");
//    }
//    public void reset(View v){
//        textViewA.setText(0 + "");
//        textViewB.setText(0 + "");
//    }

    public void change(View v){
        String str = editText.getText().toString();
        Double n = 0.0;
        try {
            n = Double.parseDouble(str);
        }catch (Exception e){
            Toast.makeText(this,"请输入正确的RMB金额",Toast.LENGTH_SHORT).show();
            return;
        }

        if(v.getId() == R.id.button2){
            textView.setText(n*dollar + "$");
        }
        else if(v.getId() == R.id.button3){
            textView.setText(n*euro + "€");
        }
        else{
            textView.setText(n*won + "₩");
        }
    }

    public void config(View v){
        Intent intent = new Intent(this,Main4Activity.class);
        intent.putExtra("dollar",dollar);
        intent.putExtra("euro",euro);
        intent.putExtra("won",won);
        startActivity(intent);
    }
}

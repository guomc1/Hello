package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textViewA;
    TextView textViewB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        editText = findViewById(R.id.editText);
//        textView2 = findViewById(R.id.textView2);

        textViewA = findViewById(R.id.textView19);
        textViewB = findViewById(R.id.textView17);

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

    public void addA1(View v){
        textViewA.setText(Integer.parseInt(textViewA.getText().toString()) + 1 + "");
    }
    public void addA2(View v){
        textViewA.setText(Integer.parseInt(textViewA.getText().toString()) + 2 + "");
    }
    public void addA3(View v){
        textViewA.setText(Integer.parseInt(textViewA.getText().toString()) + 3 + "");
    }
    public void addB1(View v){
        textViewB.setText(Integer.parseInt(textViewB.getText().toString()) + 1 + "");
    }
    public void addB2(View v){
        textViewB.setText(Integer.parseInt(textViewB.getText().toString()) + 2 + "");
    }
    public void addB3(View v){
        textViewB.setText(Integer.parseInt(textViewB.getText().toString()) + 3 + "");
    }
    public void reset(View v){
        textViewA.setText(0 + "");
        textViewB.setText(0 + "");
    }
}

package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView2 = findViewById(R.id.textView2);
    }

    public void change(View v){
        String str = editText.getText().toString();
        Double n = 0.0;
        if(str.isEmpty() || str.length() == 0){
            textView2.setText("输入格式为以C（摄氏度）或F（华氏度）结尾");
        }
        else if(str.charAt(str.length()-1) == 'C'){
            try {
                n = Double.parseDouble(str.substring(0, str.length() - 1));
            }catch (Exception e){
                textView2.setText("请输入正确的温度");
                return;
            }
            textView2.setText(String.format("%.2f",n * 1.8 + 32) + "F");
        }
        else if(str.charAt(str.length()-1) == 'F'){
            try {
                n = Double.parseDouble(str.substring(0, str.length() - 1));
            }catch (Exception e){
                textView2.setText("请输入正确的温度");
                return;
            }
            textView2.setText(String.format("%.2f",(n - 32) / 1.8) + "C");
        }
        else{
            textView2.setText("输入格式为以C（摄氏度）或F（华氏度）结尾");
        }
    }
}

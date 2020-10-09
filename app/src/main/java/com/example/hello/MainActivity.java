package com.example.hello;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "MainActivity";

    Handler handler;
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

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    String str = (String) msg.obj;
                    dollar = 1 / (Double.parseDouble(str.split(",")[2]) / 100);
                    won = 1 / (Double.parseDouble(str.split(",")[1]) / 100);
                    euro = 1 / (Double.parseDouble(str.split(",")[0]) / 100);

                    SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    dollar = (double)sp.getFloat("dollar",(float)dollar);
                    euro = (double)sp.getFloat("euro",(float)euro);
                    won = (double)sp.getFloat("won",(float)won);

                    Log.i(TAG, "handleMessage: msg =" + str);
                }
                super.handleMessage(msg);
            }
        };


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
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar",(float)dollar);
        editor.putFloat("euro",(float)euro);
        editor.putFloat("won",(float)won);
        editor.apply();
//        Bundle bundle = new Bundle();
//        bundle.putDouble("dollar",dollar);
//        bundle.putDouble("euro",euro);
//        bundle.putDouble("won",won);
//        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == 1 && resultCode == 1){
//            Bundle bundle = data.getExtras();
//            dollar = bundle.getDouble("dollar",0.0);
//            euro = bundle.getDouble("euro",0.0);
//            won = bundle.getDouble("won",0.0);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == 1){
            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            dollar = (double)sp.getFloat("dollar",0.0f);
            euro = (double)sp.getFloat("euro",0.0f);
            won = (double)sp.getFloat("won",0.0f);
    //        Bundle bundle = data.getExtras();
    //        dollar = bundle.getDouble("dollar",0.0);
    //        euro = bundle.getDouble("euro",0.0);
    //        won = bundle.getDouble("won",0.0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void run(){
//        URL url = null;
//        String html = "";
//        try{
//            url = new URL("https://www.usd-cny.com/bankofchina.htm");
//            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            html = inputStream2String(in);
//            Log.i(TAG, "run: html:" + html);
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        String url = "https://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        }catch (IOException e){
            e.printStackTrace();
        }

        String message = getMessage(doc);

        Message msg = handler.obtainMessage(1);
        msg.obj = message;
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz < 0) break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

    private String getMessage(Document doc){
        Elements tables = doc.getElementsByTag("table");
        Element table = tables.get(0);

        Elements trs = table.getElementsByTag("tr");
        Element tr_euro = trs.get(7);
        Element tr_won = trs.get(13);
        Element tr_dollar = trs.get(26);

        Element euro = tr_euro.getElementsByTag("td").get(5);
        Element won = tr_won.getElementsByTag("td").get(5);
        Element dollar = tr_dollar.getElementsByTag("td").get(5);

        return euro.text() + "," + won.text() + "," + dollar.text();

    }
}

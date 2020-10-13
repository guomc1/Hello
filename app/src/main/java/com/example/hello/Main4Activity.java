package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.LocusId;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main4Activity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener{

    SharedPreferences sp;
    EditText editText1,editText2,editText3;
    ListView listView;
    Handler handler;
    String allRate,date;
    double dollar,euro,won;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        dollar = bundle.getDouble("dollar",0.0);
//        euro = bundle.getDouble("euro",0.0);
//        won = bundle.getDouble("won",0.0);

        sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollar = (double)sp.getFloat("dollar",0.0f);
        euro = (double)sp.getFloat("euro",0.0f);
        won = (double)sp.getFloat("won",0.0f);

        editText1 = findViewById(R.id.editText5);
        editText2 = findViewById(R.id.editText6);
        editText3 = findViewById(R.id.editText7);

        listView = findViewById(R.id.mylist);
        listView.setOnItemClickListener(this);

        editText1.setText("dollar rate =" + dollar);
        editText2.setText("euro rate =" + euro);
        editText3.setText("won rate =" + won);

        sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
        date = sp.getString("date","");

        //当前日期
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(now);

        //是同一天则直接从文件中读出数据
        if(date.equals(nowDate)){
            allRate = sp.getString("allRate","");
            String[] rateArray = allRate.split(",");
            List<Currency> list = new ArrayList<>();
            for(String s:rateArray){
                Currency currency = new Currency();
                currency.setName(s.split(":")[0]);
                currency.setRate(s.split(":")[1]);
                list.add(currency);
            }
            MyAdapter myAdapter = new MyAdapter(Main4Activity.this,
                    R.layout.list_item,
                    list);
            listView.setAdapter(myAdapter);
//            listView.setAdapter(new SimpleAdapter(Main4Activity.this,
//                    list,
//                    R.layout.list_item,
//                    new String[] {"itemTitle","itemDetail"},
//                    new int[] {R.id.itemTitle,R.id.itemDetail}));
        }
        //不是同一天则从网络中获取数据，写入新的数据和日期
        else{
            //从网络中获取数据
            getData();

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("date",nowDate);
            editor.apply();
        }
    }

    private void getData(){
        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 2){
                    String allRate = "";
                    List<Currency> list = (List<Currency>) msg.obj;

                    MyAdapter myAdapter = new MyAdapter(Main4Activity.this,
                            R.layout.list_item,
                            list);
//                    listView.setAdapter(new SimpleAdapter(Main4Activity.this,
//                            list,
//                            R.layout.list_item,
//                            new String[] {"itemTitle","itemDetail"},
//                            new int[] {R.id.itemTitle,R.id.itemDetail}));
                    listView.setAdapter(myAdapter);

                    for(Currency currency:list){
                        allRate += currency.getName() + ":" + currency.getRate() + ",";
                    }

                    sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("allRate",allRate);
                    editor.apply();
                }
                super.handleMessage(msg);
            }
        };
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
        setResult(1,intent);
        finish();
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

        List<Currency> message = getMessage(doc);

        Message msg = handler.obtainMessage(2);
        msg.obj = message;
        handler.sendMessage(msg);
    }

    private List<Currency> getMessage(Document doc){
        Elements tables = doc.getElementsByTag("table");
        Element table = tables.get(0);

        List<Currency> list = new ArrayList<>();

        Elements trs = table.getElementsByTag("tr");
        Element e = null;
        for(int i = 1;i < trs.size();i++){
            e = trs.get(i);
            Currency currency = new Currency();
            currency.setName(e.getElementsByTag("td").get(0).text());
            currency.setRate(e.getElementsByTag("td").get(5).text());
            list.add(currency);
        }

        return list;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Currency currency = (Currency) listView.getItemAtPosition(position);
        String name = currency.getName();
        String rate = currency.getRate();

        Intent intent = new Intent(this,Main5Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("rate",rate);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }
}

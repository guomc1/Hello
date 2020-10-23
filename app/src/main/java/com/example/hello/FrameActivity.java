package com.example.hello;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrameActivity extends FragmentActivity {

    private Fragment fragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtInfo,rbtSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments);
        fragments = new Fragment[3];
        fragmentManager= getSupportFragmentManager();
        fragments[0] = fragmentManager.findFragmentById(R.id.fragment1);
        fragments[1] = fragmentManager.findFragmentById(R.id.fragment2);
        fragments[2] = fragmentManager.findFragmentById(R.id.fragment3);
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(fragments[0]).hide(fragments[1]).hide(fragments[2]);
        fragmentTransaction.show(fragments[0]).commit();

        rbtHome = findViewById(R.id.radio1);
        rbtInfo = findViewById(R.id.radio2);
        rbtSetting = findViewById(R.id.radio3);

        radioGroup = findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(fragments[0]).hide(fragments[1]).hide(fragments[2]);
                switch (i){
                    case R.id.radio1:
                        fragmentTransaction.show(fragments[0]).commit();
                        break;
                    case R.id.radio2:
                        fragmentTransaction.show(fragments[1]).commit();
                        break;
                    case R.id.radio3:
                        fragmentTransaction.show(fragments[2]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}

package com.example.pancerpuger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

public class landing extends AppCompatActivity {
    Button nelayan, konsumen, admin, mitra;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        nelayan = (Button) findViewById(R.id.nelayan);
        konsumen = (Button) findViewById(R.id.konsumen);
        admin = (Button) findViewById(R.id.admin);
        mitra = (Button) findViewById(R.id.mitra);


    }
}
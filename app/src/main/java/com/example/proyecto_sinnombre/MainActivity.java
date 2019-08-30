package com.example.proyecto_sinnombre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Cambiar(View view){
        Intent bnavigation = new Intent(this,MainApp.class);
        startActivity(bnavigation);
    }
}

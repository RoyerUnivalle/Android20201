package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    String name,passwd;
    TextView showData, tvContador;
    int contador=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //enlace
        showData = findViewById(R.id.tvData);
        tvContador = findViewById(R.id.tvContador);
        Bundle data = getIntent().getExtras();
        name = data.getString("name");
        passwd = data.getString("passwd");
        showData.setText("name: " + name + " passwd: "+ passwd);
        if(savedInstanceState != null){
            contador = savedInstanceState.getInt("contador");
            tvContador.setText("Contador: "+ contador);
        }
    }

    public void backToLogin(View g) {
        Intent ir = new Intent(this,MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    public void contar(View q){
        contador += 1;
        tvContador.setText("Contador: "+ contador);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contador",contador);
    }
}

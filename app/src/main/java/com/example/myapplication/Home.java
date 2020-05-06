package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    String name,passwd;
    TextView showData, tvContador;
    int contador=0;
    Button pintar;
    Pintar Obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //enlace
        showData = findViewById(R.id.tvData);
        tvContador = findViewById(R.id.tvContador);
        pintar = findViewById(R.id.btnPintar);
        Bundle data = getIntent().getExtras();
        name = data.getString("name");
        passwd = data.getString("passwd");
        showData.setText("name: " + name + " passwd: "+ passwd);
        if(savedInstanceState != null){
            contador = savedInstanceState.getInt("contador");
            tvContador.setText("Contador: "+ contador);
        }
        Obj = new Pintar();
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

    public void pintar(View q) throws InterruptedException {
        Obj = new Pintar();
        Obj.execute("hola");
        /*if(Obj != null){
            Obj.execute("hola");
        }else {
            Obj.cancel(true);
            Obj.execute("hola");
        }*/
        /*for(int i= 0; i<=20; i++){
            Thread.sleep(1000);
            pintar.setBackgroundColor(Color.rgb(getRamdon(),getRamdon(),getRamdon()));
        }*/
    }

    public int getRamdon(){
        return  (int)(Math.random()*255 + 1);   // Esto da valores entre 1 y 255
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contador",contador);
    }

    class  Pintar extends AsyncTask<String,Integer,String>{
        String mensaje;
        @Override
        protected String doInBackground(String... msj) { // No acceso a elementos de la UI
            mensaje = msj[0];
            for(int i= 0; i<=20; i++){
                if(!isCancelled()){
                    try {
                        Thread.sleep(1000);
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(String aVoid) {
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
            super.onPostExecute(aVoid);
        }
        @Override // segundo parametro de la calse
        protected void onProgressUpdate(Integer... numero) { // si tiene acceso a la UI
            pintar.setBackgroundColor(Color.rgb(getRamdon(),getRamdon(),getRamdon()));
            pintar.setText("i: "+ numero[0]);
            super.onProgressUpdate(numero);
        }

        @Override
        protected void onCancelled(String s) {
            Toast.makeText(getApplicationContext(), "Hilo Cancelado", Toast.LENGTH_LONG);
            Obj = null;
            super.onCancelled(s);
        }

        public int getRamdon(){
            return  (int)(Math.random()*255 + 1);   // Esto da valores entre 1 y 255
        }
    }

}

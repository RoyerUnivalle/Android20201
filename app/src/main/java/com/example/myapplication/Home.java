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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Home extends AppCompatActivity {

    String name,passwd;
    TextView showData, tvContador;
    int contador=0;
    Button pintar;
    Pintar Obj = null;
    String DATA_URL = "https://invessoft.com/api/eventos/1";
    Consultar obj2 = null;


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

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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

    public void consultar(View q){
        obj2 = new Consultar();
        obj2.execute();
    }

    public void consultar2(View q){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // https://docs.oracle.com/javaee/7/api/javax/json/JsonArray.html
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        //obj2.execute();
    }

    public int getRamdon(){
        return  (int)(Math.random()*255 + 1);   // Esto da valores entre 1 y 255
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contador",contador);
    }

    // Clase
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

    class Consultar extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            URL urlConexion = null;
            try {
                urlConexion = new URL(DATA_URL);
                // primer paso segun https://developer.android.com/reference/java/net/HttpURLConnection.html
                HttpURLConnection conexion = (HttpURLConnection) urlConexion.openConnection();
                //segundo paso -headers
                conexion.setDoOutput(true);
                conexion.setDoInput(true);
                conexion.setRequestMethod("GET");
                conexion.setUseCaches(false);
                conexion.setConnectTimeout(10000);
                conexion.setReadTimeout(10000);
                conexion.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                // conexion.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                conexion.connect();
                // debemos consultar si esta con estado 200
                int connectionStatus = conexion.getResponseCode();
                if(connectionStatus == HttpURLConnection.HTTP_OK){// LA CONEXION FUE EXITOSA
                    System.out.println("HOLA RESPUESTA "+conexion.getInputStream());
                    //conexion.getInputStream();
                }else {
                    System.out.println(connectionStatus);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}

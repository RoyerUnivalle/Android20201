package com.example.myapplication.servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Servicio extends Service {

    String fecha;
    SimpleDateFormat dateFormat;
    MostrarHora objHora;

    @Override
    public void onCreate() {
        super.onCreate();
        dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    }

    public Servicio() {
    }

    //startService()
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //bindService
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       System.out.println("hola sericio"+ startId);
       //Obteniendo datos de la red por medio ConectyManagery Network Info.
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        if(isWifiConn){
            //Aqui trabaja en el mismo hila de la UI de donde se invoco por medio de startService
            for (int j=1; j<=10; j++){
            try {
                Toast.makeText(this, "Fecha: " + fecha, Toast.LENGTH_LONG).show();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }else if (isMobileConn){
            // Utulizar otro hilo por medio de AsynTask
            objHora = new MostrarHora();
            objHora.execute();
        }else {
            Toast.makeText(this, "Sin conexion: ", Toast.LENGTH_LONG).show();
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class MostrarHora extends AsyncTask<Void,String,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            for (int j=1; j<=10; j++){
                try {
                    fecha = dateFormat.format(new Date());
                    publishProgress("Fecha y hora: " + fecha);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... fechaHora) {
            Toast.makeText(getApplicationContext(), fechaHora[0], Toast.LENGTH_LONG).show();
            super.onProgressUpdate(fechaHora);
        }
    }
}

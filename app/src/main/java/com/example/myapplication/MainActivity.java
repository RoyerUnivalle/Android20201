package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1;
    Button btn2;
    EditText name, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /// enlace
        passwd = findViewById(R.id.etPasswd);
        name = findViewById(R.id.etName);
        btn1 = findViewById(R.id.button2);
        btn2 = findViewById(R.id.button3);

        // add event to button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Method Onclick delegate", Toast.LENGTH_LONG).show();
            }
        });
        // add event to button interface Onclick
        btn2.setOnClickListener(this);
    }

    public void showMessage(View obj){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Hola Boton positivo", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage("Hola mensaje personalizado");
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Hola OnStart", Toast.LENGTH_LONG).show();;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Hola onPause", Toast.LENGTH_LONG).show();;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Hola onStop", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.button3:
        Toast.makeText(this, "Hola btn 2 Interface", Toast.LENGTH_LONG).show();
        break;
        default:
        Toast.makeText(this, "Hola default", Toast.LENGTH_LONG).show();
        }
    }

    public void goToHome(View k){
        Intent ir = new Intent(this,Home.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle data = new Bundle();
        data.putString("name",name.getText().toString());
        data.putString("passwd",passwd.getText().toString());
        ir.putExtras(data);
        startActivity(ir);
    }
}

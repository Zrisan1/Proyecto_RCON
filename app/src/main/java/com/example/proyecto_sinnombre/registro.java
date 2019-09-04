package com.example.proyecto_sinnombre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText etnom,etemail,etpass;
    private String nombre = "";
    private String correo = "";
    private String contra = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        etnom = (EditText)findViewById(R.id.etuser);
        etemail = (EditText)findViewById(R.id.etemail);
        etpass = (EditText)findViewById(R.id.etpass);
    }

    public void regresar(View view){
        startActivity(new Intent(registro.this,Login.class));
    }

    public void registro(View view){
        nombre = etnom.getText().toString();
        correo = etemail.getText().toString();
        contra = etpass.getText().toString();

        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contra)){
            procesoregistro();
        }else if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(correo)){
            etpass.setError("Tienes que escribir el password");
        }else if(!TextUtils.isEmpty(nombre)){
            etemail.setError("Tienes que escribir el email");
        }else{
            etnom.setError("Tienes que escribir el nombre de usuario");
        }
    }

    public void procesoregistro(){

        mAuth.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String id = mAuth.getCurrentUser().getUid();

                    Map<String, Object> datapersona = new HashMap<>();

                    datapersona.put("id",id);
                    datapersona.put("nombre",nombre);
                    datapersona.put("correo",correo);
                    datapersona.put("password",contra);

                    mDatabase.child(id).setValue(datapersona).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(registro.this,Login.class));
                                finish();
                            }else{
                                Toast.makeText(registro.this, "asdasdad", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(registro.this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                    etemail.setError("Correo no valido");
                }
            }
        });
    }
}

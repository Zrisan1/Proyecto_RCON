package com.example.proyecto_sinnombre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText txtuser,txtpass;
    private TextView tvnewuser;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtuser = (EditText)findViewById(R.id.etuser);
        txtpass = (EditText)findViewById(R.id.etpass);
        tvnewuser = (TextView)findViewById(R.id.tvcreateaccount);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true); //working offline


        tvnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,registro.class));
            }
        });
    }

    public void loginwithdatos(View view){
        String usuario = txtuser.getText().toString();
        String password = txtpass.getText().toString();

        if(!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(usuario,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
//                        check if user is exist or not in database, not in auth
//                            cz auth automatically checks it, it is needed if your app
//                            has fb, google login as well as general login
                        checkUserExists();
                        Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String error = task.getException().getMessage();
                        Toast.makeText(Login.this, "Error "+error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(!TextUtils.isEmpty(usuario)){
            txtpass.setError("Tienes que escribir el password");
        }else{
            txtuser.setError("Tienes que escribir el username");
        }
    }

    private void checkUserExists() {

        if(mAuth.getCurrentUser()!=null)
        {
            final String user_id = mAuth.getCurrentUser().getUid();

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(user_id))
                    {
                        Intent intent = new Intent(Login.this,MainApp.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(Login.this,registro.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
}

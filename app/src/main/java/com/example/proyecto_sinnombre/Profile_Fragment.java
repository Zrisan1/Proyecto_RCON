package com.example.proyecto_sinnombre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Profile_Fragment extends Fragment {

    private ListView lv1;
    private TextView tv1,tv_nombre,tv_email;
    private Button btn1;

    private String opciones_profile[] = {"Reputacion como vendedor","Ver mi periodo de facturacion","Cambiar constraseña"};

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseusers;
    private FirebaseAuth mAuth;

    public Profile_Fragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container,false);


        lv1 = (ListView)view.findViewById(R.id.options_profile);
        tv1 = (TextView)view.findViewById(R.id.title_profile1);
        tv_nombre = (TextView)view.findViewById(R.id.txt_nombre);
        tv_email = (TextView)view.findViewById(R.id.txt_correo);
        btn1 = (Button)view.findViewById(R.id.btn_close_session);

        ArrayAdapter<String> adapter_opciones_profile = new ArrayAdapter<String>(getActivity(), R.layout.list_item_options_profile,opciones_profile);
        lv1.setAdapter(adapter_opciones_profile);

        mDatabaseusers = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        try {
            String titulo_perfil = "Mi Perfil";
//            String texto_nombre = tv_nombre.getText().toString();
//
//            SpannableString texto1 = new SpannableString(titulo_perfil);
//            SpannableString texto2 = new SpannableString(texto_nombre);
//
//            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
//
//            texto1.setSpan(boldSpan,0,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            texto2.setSpan(boldSpan,0,texto_nombre.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            tv1.setText(texto1);
//            tv_nombre.setText(texto2);
        }catch (Exception e){
            Toast.makeText(getActivity(), "Error: "+ e, Toast.LENGTH_SHORT).show();
        }

        tv_email.setText(user.getEmail().toString());

        mDatabaseusers.child(user.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    tv_nombre.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
            }
        });

        return view;

    }



    public void logout(View view){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

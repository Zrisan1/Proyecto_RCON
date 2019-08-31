package com.example.proyecto_sinnombre;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Profile_Fragment extends Fragment {

    ListView lv1;
    TextView tv1,tv_nombre,tv_email;

    private String opciones_profile[] = {"Reputacion como vendedor","Ver mi periodo de facturacion","Cambiar constraseña"};

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

        ArrayAdapter<String> adapter_opciones_profile = new ArrayAdapter<String>(getActivity(), R.layout.list_item_options_profile,opciones_profile);
        lv1.setAdapter(adapter_opciones_profile);

        String titulo_perfil = "Mi Perfil";
        String texto_nombre = "Nombre Apellido";

        SpannableString texto1 = new SpannableString(titulo_perfil);
        SpannableString texto2 = new SpannableString(texto_nombre);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

        texto1.setSpan(boldSpan,0,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        texto2.setSpan(boldSpan,0,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv1.setText(texto1);
        tv_nombre.setText(texto2);
        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

package com.example.proyecto_sinnombre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

public class MainApp extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    CargarFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    CargarFragment(new Producto_Fragment());
                    return true;
                case R.id.navigation_dashboard2:
                    CargarFragment(new Ventas_Fragment());
                    return true;
                case R.id.navigation_profile:
                    CargarFragment(new Profile_Fragment());
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CargarFragment(new HomeFragment());

        ////////////////////////----------R-------//////////////////////
        //Obtiene valor de preferencia (la primera ocasión es por default true).
        boolean muestra = getValuePreference(getApplicationContext());

        //Valida si muestra o no LicenseActivity.
        if(muestra){
            Intent myIntent = new Intent(this, OnBoardingActivity.class);
            startActivity(myIntent);
            saveValuePreference(getApplicationContext(), false);

        }
        ///////////////////////---------FIN R -----////////////////////

    }

    public void CargarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.MainFrameLayout,fragment);
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out,android.R.animator.fade_in,android.R.animator.fade_out);
        transaction.commit();

    }

    ///////////////////////-------- R ----------////////////////////
    private String PREFS_KEY = "mispreferencias";

    public void saveValuePreference(Context context, Boolean mostrar) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("license", mostrar);
        editor.commit();
    }



    public boolean getValuePreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getBoolean("license", true);
    }
    ///////////////////////---------FIN R -----////////////////////

}

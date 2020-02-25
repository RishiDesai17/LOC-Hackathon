package com.example.lineofsafety;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        SharedPreferences version = getSharedPreferences( "VersionControl", Context.MODE_PRIVATE );
        int ver = version.getInt( "Version_Key", -1 );
        if (ver == -1) {
            Intent signup = new Intent( MainActivity.this, SignUp.class );
            startActivity( signup );
            this.finish();
        } else {
            setContentView( R.layout.activity_main );
        }

        loadFragment( new HomeFragment() );
        BottomNavigationView bottomnav =findViewById( R.id.bottom_navigation );
        bottomnav.setOnNavigationItemSelectedListener( navlistener );

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case R.id.action_home:
                    loadFragment( new HomeFragment() );
                    break;
                case R.id.action_record:
                    loadFragment( new Record() );
                    break;
                case R.id.action_user:
                    loadFragment( new UserData() );
                    break;
            }
            return true;
        }
    };
    public void loadFragment(Fragment frag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container,frag,"My_Fragment");
        ft.addToBackStack(null);
        ft.commit();
    }




}

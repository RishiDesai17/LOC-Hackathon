package com.example.lineofsafety;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    Spinner bloodGrp_select;
    EditText Username;
    EditText Userdob;
    EditText Usermed;

    EditText phone_1;
    EditText phone_2;
    EditText phone_3;

    String gend;
    Button save;
    RadioButton rm;
    RadioButton rf;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.sign_up );

        Username = findViewById( R.id.editTextName );
        Userdob = findViewById( R.id.editTextDOB );
        Usermed = findViewById( R.id.editTextMedNotes );
        phone_1 = findViewById( R.id.editPhone_1 );
        phone_2 = findViewById( R.id.editPhone_2 );
        phone_3 = findViewById( R.id.editPhone_3 );
        bloodGrp_select = findViewById( R.id.spinner2BloodGrp );
        save = findViewById( R.id.save );
        myDatabase = new MyDatabase();
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        } );

    }

    public void addUser()
    {

        String name = Username.getText().toString();
        String dob = Userdob.getText().toString();
        String medN = Usermed.getText().toString();

        String ph_one = phone_1.getText().toString();
        String ph_two = phone_2.getText().toString();
        String ph_three = phone_3.getText().toString();
        String bloodG = bloodGrp_select.getSelectedItem().toString();
        if(name.isEmpty() && dob.isEmpty() && medN.isEmpty() && bloodG.isEmpty() && gend.isEmpty() && ph_one.isEmpty() && ph_two.isEmpty() && ph_three.isEmpty())
        {
            Toast.makeText( this, "Incomplete Information!", Toast.LENGTH_LONG ).show();
        }
        else
        {
            int id = myDatabase.insertData( name,dob,gend,bloodG,medN,ph_one,ph_two,ph_three,this );
            if(id<=0)
            {
                Toast.makeText( this, "Unsuccessful!", Toast.LENGTH_LONG ).show();
                Username.setText( "" );
                Userdob.setText( "" );
                Usermed.setText( "" );

            }
            else
            {
                int ver = BuildConfig.VERSION_CODE;
                SharedPreferences version = getSharedPreferences( "VersionControl",Context.MODE_PRIVATE );
                version.edit().putInt("Version_Key",ver ).apply();
                Toast.makeText( this, "Welcome!", Toast.LENGTH_SHORT ).show();
                Username.setText( "" );
                Userdob.setText( "" );
                Usermed.setText( "" );

                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity( intent );
                this.finish();
            }
        }
    }

    public void onCheckedItemClicked(View view)
    {
        rm = findViewById( R.id.radioMale );
        rf = findViewById( R.id.radioFemale );
        boolean check = ((RadioButton)view).isChecked();

        switch(view.getId())
        {
            case R.id.radioMale:
                if(check)
                {
                    gend = "Male";
                    rm.toggle();
                    break;
                }

            case R.id.radioFemale:
                if(check)
                {
                    gend = "Female";
                    rf.toggle();
                    break;
                }

        }
    }
}

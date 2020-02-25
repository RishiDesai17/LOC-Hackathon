package com.example.lineofsafety;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class UserData extends Fragment {

    TextView nameView;
    TextView genderView;
    TextView bloodView;
    TextView dobView;
    TextView medicalView;
    TextView phone_one;
    TextView phone_two;
    TextView phone_three;
    Context context;

    Button checkP;
    public int CALLING_RIGHTS = 1;
    public int SMS_RIGHTS = 1;
    public int CAMERA_RIGHTS = 1;
    public int MIC_RIGHTS = 1;
    public int STORAGE_RIGHTS = 1;
    public int LOCATION_RIGHTS = 1;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    MyDatabase myDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.login,null );
        checkP = view.findViewById( R.id.checkPermission );
        nameView = view.findViewById( R.id.viewName );
        genderView = view.findViewById( R.id.viewGender );
        bloodView = view.findViewById( R.id.viewBlood );
        dobView = view.findViewById( R.id.viewDob );
        medicalView = view.findViewById( R.id.viewMednotes );
        phone_one = view.findViewById( R.id.phone_no_1 );
        phone_two = view.findViewById( R.id.phone_no_2 );
        phone_three = view.findViewById( R.id.phone_no_3 );
        myDatabase = new MyDatabase();

        nameView.setText( myDatabase.getName(context) );
        genderView.setText( myDatabase.getGender(context) );
        bloodView.setText( myDatabase.getBlood(context) );
        dobView.setText( myDatabase.getDob(context) );
        medicalView.setText( myDatabase.getMed(context) );
        phone_one.setText( myDatabase.getPhone1( context ) );
        phone_two.setText( myDatabase.getPhone2( context ) );
        phone_three.setText( myDatabase.getPhone3( context ) );

        checkP.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALLING_RIGHTS);
                }
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.SEND_SMS}, SMS_RIGHTS);
                }
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_RIGHTS);
                }
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.RECORD_AUDIO}, MIC_RIGHTS);
                }
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_RIGHTS);
                }
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_RIGHTS);
                }
                else
                    {
                        checkP.setEnabled( false );
                        //Toast.makeText( context, "All Permissions are granted!", Toast.LENGTH_SHORT ).show();
                    }
            }
        } );

        return view;

    }


    public void onAttach(Context context) {
        super.onAttach( context );
        this.context = context;
    }
}


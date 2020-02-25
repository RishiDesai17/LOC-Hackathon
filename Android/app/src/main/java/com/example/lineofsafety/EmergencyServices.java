package com.example.lineofsafety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class EmergencyServices extends AppCompatActivity implements OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    double lati,longi;
    String location;
    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    int Location_access = 1;

    MyDatabase myDatabase;
    String p1,p2,p3;

    ImageButton CallEm1;
    ImageButton CallEm2;
    ImageButton CallEm3;

    TextView call1_label;
    TextView call2_label;
    TextView call3_label;

    Button EmergencyHelp;
    ImageButton record;
    boolean recording = false;
    boolean playing = false;
    ImageButton play;
    String audioFileName = "AudioFileName.txt";
    String audioDate = "AudioDateName.txt";
    String batteryStats = "BatteryStatus.txt";
    String locateHist = "LocationHistory.txt";
    FileOutputStream fos_l;
    FileOutputStream fos_b;
    FileOutputStream fos_d;
    FileInputStream fis_d;
    FileOutputStream fos_m;
    FileInputStream fis_m;
    boolean hasCameraFlash = false;
    public int CALLING_RIGHTS = 1;
    public int SMS_RIGHTS = 1;
    public int CAMERA_RIGHTS = 1;
    boolean isflashLightOn = false;
    boolean isblinking = false;
    boolean threadRunning = false;
    int count = 0;
    public MediaPlayer SirenSound;
    Thread th = null;

    Context c;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_emergency_services );

        hasCameraFlash = getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_FLASH ); // checks if the device has a camera flash



        call1_label = findViewById( R.id.call_label1 );
        call2_label = findViewById( R.id.call_label2 );
        call3_label = findViewById( R.id.call_label3 );

        CallEm1 = findViewById( R.id.Call1 );
        CallEm2 = findViewById( R.id.Call2 );
        CallEm3 = findViewById( R.id.Call3 );
        EmergencyHelp = findViewById( R.id.diallEmergency );

        myDatabase = new MyDatabase();
        final String phone_1 = myDatabase.getPhone1( this );
        final String phone_2 = myDatabase.getPhone2( this );
        final String phone_3 = myDatabase.getPhone3( this );
        p1 = phone_1;
        p2 = phone_2;
        p3 = phone_3;
        final Context context = this;
        c=context;
        call1_label.setText( phone_1 );
        call2_label.setText( phone_2 );
        call3_label.setText( phone_3 );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        record = findViewById( R.id.recording );
        record.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!recording) {
                    Toast.makeText( context, "Recording..", Toast.LENGTH_LONG ).show();
                    record.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_mic_1, null ) );
                    recording = true;
                    setRecord(recording);
                    storeBatteryStatus();
                    storeLocation();
                }
                else
                {
                    Toast.makeText( context, "Recording Stopped!", Toast.LENGTH_LONG ).show();
                    record.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_mic_2, null ) );


                    recording = false;
                    setRecord(recording);
                }
            }
        } );

        /*play = findViewById( R.id.playRec );
        play.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!playing)
                {
                    play.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_pause,null ) );
                    playing = true;
                    setPlay(playing);
                }
                else
                {
                    play.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_play,null) );
                    playing = false;
                    setPlay(playing);
                }


            }
        } );*/

        EmergencyHelp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);

                        String mod = "tel:"  + "112"; // concatenating the number with prefix 'tel:'
                        callIntent.setData( Uri.parse(mod));
                        if(count == 1 || isflashLightOn || threadRunning) {
                            if (count == 1 && isflashLightOn && threadRunning) {
                                SirenOnOff();
                                flashLightOff();
                                threadRunning = false;
                                th.interrupt();

                            } else if (count == 1 || isflashLightOn) {
                                if (count == 1) {
                                    SirenOnOff();
                                } else {
                                    flashLightOff();
                                }

                            } else if (threadRunning) {
                                threadRunning = false;
                                th.interrupt();
                            }

                            new AlertDialog.Builder(EmergencyServices.this)
                                    .setTitle("Please Confirm!")
                                    .setTitle("App under development, dialing 112 is not permitted!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    })
                                    .create().show();

                            //startActivity(callIntent);
                        }
                        else
                        {
                            new AlertDialog.Builder(EmergencyServices.this)
                                    .setTitle("Please Confirm!")
                                    .setTitle("App under development, dialing 112 is not permitted!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    })
                                    .create().show();
                            //startActivity(callIntent);
                        }


                }
                else
                {
                    ActivityCompat.requestPermissions(EmergencyServices.this, new String[]{Manifest.permission.CALL_PHONE}, CALLING_RIGHTS);  // requests 'Phone' permission
                }
            }
        } );
        CallEm1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    if(phone_1!=null) {
                        String mod = "tel:"  + phone_1; // concatenating the number with prefix 'tel:'
                        callIntent.setData( Uri.parse(mod));
                        if(count == 1 || isflashLightOn || threadRunning) {
                            if (count == 1 && isflashLightOn && threadRunning) {
                                SirenOnOff();
                                flashLightOff();
                                threadRunning = false;
                                th.interrupt();

                            } else if (count == 1 || isflashLightOn) {
                                if (count == 1) {
                                    SirenOnOff();
                                } else {
                                    flashLightOff();
                                }

                            } else if (threadRunning) {
                                threadRunning = false;
                                th.interrupt();
                            }
                            startActivity(callIntent);
                        }
                        else
                        {
                            startActivity(callIntent);
                        }

                    }
                    else
                    {
                        Toast.makeText(EmergencyServices.this,"Sorry, couldn't find any Emergency Contact.",Toast.LENGTH_LONG).show(); // this message is shown when emergency contact is absent
                    }

                }
                else
                {
                    ActivityCompat.requestPermissions(EmergencyServices.this, new String[]{Manifest.permission.CALL_PHONE}, CALLING_RIGHTS);  // requests 'Phone' permission
                }
            }

        } );
        CallEm2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    if(phone_2!=null) {
                        String mod = "tel:"  + phone_2; // concatenating the number with prefix 'tel:'
                        callIntent.setData( Uri.parse(mod));
                        if(count == 1 || isflashLightOn || threadRunning) {
                            if (count == 1 && isflashLightOn && threadRunning) {
                                SirenOnOff();
                                flashLightOff();
                                threadRunning = false;
                                th.interrupt();

                            } else if (count == 1 || isflashLightOn) {
                                if (count == 1) {
                                    SirenOnOff();
                                } else {
                                    flashLightOff();
                                }

                            } else if (threadRunning) {
                                threadRunning = false;
                                th.interrupt();
                            }
                            startActivity(callIntent);
                        }
                        else
                        {
                            startActivity(callIntent);
                        }

                    }
                    else
                    {
                        Toast.makeText(EmergencyServices.this,"Sorry, couldn't find any Emergency Contact.",Toast.LENGTH_LONG).show(); // this message is shown when emergency contact is absent
                    }

                }
                else
                {
                    ActivityCompat.requestPermissions(EmergencyServices.this, new String[]{Manifest.permission.CALL_PHONE}, CALLING_RIGHTS);  // requests 'Phone' permission
                }
            }

        } );
        CallEm3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    if(phone_3!=null) {
                        String mod = "tel:"  + phone_3; // concatenating the number with prefix 'tel:'
                        callIntent.setData( Uri.parse(mod));
                        if(count == 1 || isflashLightOn || threadRunning) {
                            if (count == 1 && isflashLightOn && threadRunning) {
                                SirenOnOff();
                                flashLightOff();
                                threadRunning = false;
                                th.interrupt();

                            } else if (count == 1 || isflashLightOn) {
                                if (count == 1) {
                                    SirenOnOff();
                                } else {
                                    flashLightOff();
                                }

                            } else if (threadRunning) {
                                threadRunning = false;
                                th.interrupt();
                            }
                            startActivity(callIntent);
                        }
                        else
                        {
                            startActivity(callIntent);
                        }

                    }
                    else
                    {
                        Toast.makeText(EmergencyServices.this,"Sorry, couldn't find any Emergency Contact.",Toast.LENGTH_LONG).show(); // this message is shown when emergency contact is absent
                    }

                }
                else
                {
                    ActivityCompat.requestPermissions(EmergencyServices.this, new String[]{Manifest.permission.CALL_PHONE}, CALLING_RIGHTS);  // requests 'Phone' permission
                }
            }

        } );

        final Button Signal = findViewById(R.id.actFlash);   //triggers signalling by flashing the LED
        Signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)    //checking is camera permission granted already
                {
                    if(!threadRunning)
                    {
                        if(hasCameraFlash)  //checks if flashlight feature is present or not
                        {
                            blinkFlash();
                            Signal.setText("Stop LED");
                        }
                        else
                        {
                            Toast.makeText(EmergencyServices.this, "Flashlight isn't present on your device!",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        threadRunning = false;
                        th.interrupt(); // interrupts the thread out of the signalling loop
                        Signal.setText("Flash LED");
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(EmergencyServices.this, new String[] {Manifest.permission.CAMERA}, CAMERA_RIGHTS);  //requesting for camera permission
                }
            }
        });

        final Button StartStopSiren = findViewById(R.id.ActSiren);    //activates and deactivates siren and flashlight
        StartStopSiren.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(count == 0)  //checks if siren is off
                {
                    SirenOnOff();
                    if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)    //checking if camera (flash) permission is already granted
                    {
                        if(hasCameraFlash)  //checks if flashlight is present in the device
                        {
                            Toast.makeText(EmergencyServices.this, "Siren and Flashlight system activated!",Toast.LENGTH_LONG).show();
                            flashLightOn();
                        }
                        else
                        {
                            Toast.makeText(EmergencyServices.this, "Siren Activated!",Toast.LENGTH_LONG).show();
                        }
                    }
                    else    //requests permission from the user
                    {
                        ActivityCompat.requestPermissions(EmergencyServices.this, new String[] {Manifest.permission.CAMERA}, CAMERA_RIGHTS);
                    }

                    StartStopSiren.setText("Deactivate Siren");    //updates the button transcript to "Deactivate Siren"
                }
                else
                {
                    new AlertDialog.Builder(EmergencyServices.this)
                            .setTitle("Please Confirm!")
                            .setTitle("Are you sure you want to Deactivate the siren?")
                            .setPositiveButton("Yes, stop the siren and exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    SirenOnOff();
                                    if(hasCameraFlash && isflashLightOn)    //checks if flashlight is 'on' or not
                                    {
                                        flashLightOff();
                                    }
                                    StartStopSiren.setText("Blow Siren");    //updates the button transcript to "Activate Siren"
                                    Intent intent_home = new Intent(EmergencyServices.this, MainActivity.class);  // takes the user back to home page of the app
                                    startActivity(intent_home);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                }
            }
        });



    }



    public void SirenOnOff()    // this function is responsible for turning the siren 'on' and 'off'
    {
        if(count == 0 && SirenSound == null)    //ensures the siren is already 'off' to turn it back'on'
        {
            SirenSound = MediaPlayer.create(EmergencyServices.this,R.raw.danger_siren);   // declaration of the MediaPlayer object
            SirenSound.start(); // starts the intended sound
            SirenSound.setLooping(true);    // sets the sound in loop
            count = 1;  // this variable will be use to determine the state of the siren (whether it is 'on' or 'off'
        }
        else if(SirenSound!=null)   // ensures the siren is set 'on' so that it could be turned 'off'
        {
            SirenSound.setLooping(false);   // getting the sound off the loop
            SirenSound.stop();  // stopping the siren from ringing
            count = 0;
        }
    }

    public void flashLightOn()  // this function is responsible in turning the flashlight 'on'
    {
        CameraManager cm = (CameraManager)getSystemService(Context.CAMERA_SERVICE); // initializing and declaring the Camera Manager

        String cameraId = null; // initializing cameraId
        try {
            cameraId = cm.getCameraIdList()[0]; // cameraId is used to store the Id of the present camera device inside the smartphone
            cm.setTorchMode(cameraId,true); // passes the cameraId and turning the torch mode 'on'
            isflashLightOn = true;  // this boolean variable will help us determine the state of flashlight i.e 'on' or 'off'

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    public void flashLightOff()
    {
        CameraManager cm_1 = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try {
            cameraId = cm_1.getCameraIdList()[0];
            cm_1.setTorchMode(cameraId,false);
            isflashLightOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }
    public void blinkFlash()
    {
        Runnable task = new Runnable() // new Runnable Object
        {
            @Override
            public void run() {
                CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null;

                threadRunning = true;
                while (threadRunning) {
                    if (th.isInterrupted()) // checks if deactivate siren is pressed or not.
                    {
                        if(isflashLightOn)  // turns the flashlight back 'on' if it was previously lit
                        {
                            flashLightOn();
                        }
                        else    // turns the flashlight back 'off' if it was previously unlit
                        {
                            flashLightOff();
                        }
                        break;  // breaks out of the loop
                    }
                    else {
                        final String pattern = "010";  // this is the pattern in which the flashlight will blink
                        long del = 500; // this is relevant in slowing/delaying the thread to distinctly observe the effect of the flashing LED
                        int i;
                        isblinking = true;  // this variable is responsible in determining the state of LED
                        for (i = 0; i < pattern.length(); i++) {

                            try {
                                cameraId = cm.getCameraIdList()[0];
                                if (pattern.charAt(i) == '1') {
                                    cm.setTorchMode(cameraId, true);   // if the character at i-th position is 1, flashlight will be lit
                                } else {
                                    cm.setTorchMode(cameraId, false);  // if the character at i-th position is 0, flashlight will be shut
                                }
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                            try {
                                Thread.sleep(del);  // delays the thread

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        };

        th = new Thread(task);  // creates a new thread for executing the 'task' named runnable
        th.start();

    }

    public void onBackPressed() // gets triggered when user presses the back button
    {
        new AlertDialog.Builder(EmergencyServices.this)
                .setTitle("Are you sure?")
                .setMessage("If the siren is still ringing, deactivate the siren and then exit")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("No the siren isn't active, please exit now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* The below if condition
                        checks if siren and flashlight is 'off' or not, and if they're not, it turns them 'off'  */

                        if(count == 1 || isflashLightOn || threadRunning)
                        {
                            if(count == 1 && isflashLightOn && threadRunning)
                            {
                                SirenOnOff();
                                flashLightOff();
                                threadRunning=false;
                                th.interrupt();
                            }
                            else if(count == 1 || isflashLightOn)
                            {
                                if(count == 1) {
                                    SirenOnOff();
                                }
                                else
                                {
                                    flashLightOff();
                                }
                            }
                            else if(threadRunning)
                            {
                                threadRunning = false;
                                th.interrupt();
                            }
                        }


                        Intent intent_home = new Intent(EmergencyServices.this, MainActivity.class);  // directs the user directly to home activity
                        startActivity(intent_home);

                        dialog.dismiss();
                    }
                }).create().show();

    }
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }
    public String CreateRandomAudioFileName(int string)
    {
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        random = new Random();
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }
    public void storeBatteryStatus()
    {
        BatteryManager bm = (BatteryManager) getSystemService( BATTERY_SERVICE );
        int batlvl = bm.getIntProperty( BatteryManager.BATTERY_PROPERTY_CAPACITY );
        try {
            fos_b = openFileOutput( batteryStats,MODE_PRIVATE );
            fos_b.write( (batlvl+"%").getBytes() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos_b!=null)
            {
                try {
                    fos_b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void storeLocation()
    {
        try {
            fos_l = openFileOutput( locateHist ,MODE_PRIVATE);
            String loc = "Lan: "+lati+" Lon: "+longi;
            fos_l.write( loc.getBytes() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos_l!=null)
            {
                try {
                    fos_l.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void setPlay(boolean mode) throws IllegalArgumentException,
            SecurityException, IllegalStateException
    {
        if(mode)
        {
            String aud = null;
            mediaPlayer = new MediaPlayer();
            try {
                fis_m = openFileInput( audioFileName );
                InputStreamReader isr = new InputStreamReader( fis_m );
                BufferedReader br = new BufferedReader( isr );
                StringBuilder sb = new StringBuilder(  );
                while((aud=br.readLine())!=null)
                {
                    sb.append( aud );
                }
                AudioSavePathInDevice = sb.toString();
                mediaPlayer.setDataSource( AudioSavePathInDevice );
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(fis_m!=null)
                {
                    try {
                        fis_m.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            mediaPlayer.start();
        }
        else
        {
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.release();
                MediaRecorderReady();
            }
        }
    }
    public void setRecord(boolean mode)
    {
        if(mode)
        {
            AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";
            Calendar cal = Calendar.getInstance( TimeZone.getTimeZone( "GMT+5:30" ));
            String cm = null;
            int currentHour = cal.get( Calendar.HOUR_OF_DAY );
            int currentMinutes = cal.get( Calendar.MINUTE );
            if(currentMinutes<10)
            {
                cm = 0+String.valueOf( currentMinutes);
            }
            int month = cal.get( Calendar.MONTH ) + 1;
            int date = cal.get(Calendar.DATE);
            int year = cal.get(Calendar.YEAR);
            String dateTimeStamp = date+"/"+month+"/"+year+" ; "+currentHour+":"+cm;
            try {
                fos_m = openFileOutput( audioFileName,MODE_PRIVATE );
                fos_m.write( AudioSavePathInDevice.getBytes() );
                fos_d = openFileOutput( audioDate,MODE_PRIVATE );
                fos_d.write( dateTimeStamp.getBytes() );
                MediaRecorderReady();
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(fos_m!=null && fos_d!=null)
                {
                    try {
                        fos_m.close();
                        fos_d.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else
        {
            mediaRecorder.stop();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }
        else
        {
            ActivityCompat.requestPermissions(EmergencyServices.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},Location_access);
        }
        /*LatLng Sample = new LatLng(19.169257, 73.341601);
        mMap.addMarker(new MarkerOptions().position(Sample).title("Sample: Maharashtra"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Sample));*/

    }


public void sendSMS(double la, double lo)
{
    if(ContextCompat.checkSelfPermission(EmergencyServices.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)  //checking if 'sms' permission is granted
    {
        if (p1!=null || p2!=null || p3!=null) //ensuring the presence of the Emergency contact
        {
            String coord = la+","+lo;
            //Toast.makeText( context, "Sending SOS messages to Emergency Contacts", Toast.LENGTH_SHORT ).show();
            SmsManager.getDefault().sendTextMessage(p1, null, "(This is a Test)I am in Danger, please rescue me. My Location: "+coord, null, null);    //gets the default messaging service app and sends message to the emergency
            SmsManager.getDefault().sendTextMessage(p2, null, "(This is a Test)I am in Danger, please rescue me. My Location: "+coord, null, null);
            SmsManager.getDefault().sendTextMessage(p3, null, "(This is a Test)I am in Danger, please rescue me. My Location: "+coord, null, null);
        }
        else
        {
            Toast.makeText(c,"Sorry, couldn't find any Emergency Contact.",Toast.LENGTH_LONG).show(); // this message is shown when emergency contact is absent
        }
    }
    else
    {
        ActivityCompat.requestPermissions(EmergencyServices.this, new String[] {Manifest.permission.SEND_SMS}, SMS_RIGHTS);   // requests for 'sms' permission
    }

}
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(EmergencyServices.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        double lat = location.getLatitude();
        double longit = location.getLongitude();
        if(lat!=0.0 && longit!=0.0)
        {
            sendSMS(lat,longit);
            lati = lat;
            longi = longit;
        }


        LatLng latLng = new LatLng(lat,longit);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera( CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}

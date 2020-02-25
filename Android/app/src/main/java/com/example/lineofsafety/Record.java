package com.example.lineofsafety;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Record extends Fragment {

    String audioFileName = "AudioFileName.txt";
    String audioDate = "AudioDateName.txt";
    String batteryStats = "BatteryStatus.txt";
    String locateHist = "LocationHistory.txt";
    FileInputStream fis_l;
    FileInputStream fis_b;
    FileInputStream fis_d;
    FileOutputStream fos_m;
    FileInputStream fis_m;
    MediaPlayer mediaPlayer;
    ImageButton recordPlay;
    boolean playing = false;
    TextView date;
    TextView battery;
    TextView location;
    View view;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_record,null );
        recordPlay = view.findViewById( R.id.playRecording );
        date = view.findViewById( R.id.dateStamp );
        battery = view.findViewById( R.id.batteryStats );
        location = view.findViewById( R.id.locStats );
        String dateAndTime = null,batteryS = null,locationS = null;
        try {
            fis_d = context.openFileInput( audioDate );
            fis_l = context.openFileInput( locateHist );
            fis_b = context.openFileInput( batteryStats );

            InputStreamReader isr = new InputStreamReader( fis_d );
            InputStreamReader isr_2 = new InputStreamReader( fis_l );
            InputStreamReader isr_3 = new InputStreamReader( fis_b );

            BufferedReader br = new BufferedReader( isr );
            BufferedReader br_2 = new BufferedReader( isr_2 );
            BufferedReader br_3 = new BufferedReader( isr_3 );

            StringBuilder sb = new StringBuilder(  );
            StringBuilder sb_2 = new StringBuilder(  );
            StringBuilder sb_3 = new StringBuilder(  );
            while((dateAndTime=br.readLine())!=null && (batteryS=br_3.readLine())!=null && (locationS=br_2.readLine())!=null)
            {
                sb.append( dateAndTime );
                sb_2.append( locationS );
                sb_3.append( batteryS );
            }
            if(sb!=null && sb_2!=null && sb_3!=null)
            {
                dateAndTime = sb.toString();
                batteryS = sb_3.toString();
                locationS = sb_2.toString();
            }
            else
            {
                dateAndTime = "No Record";
                batteryS = "No Record";
                locationS = "No Record";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis_d!=null && fis_l!=null && fis_b!=null)
            {
                try {
                    fis_d.close();
                    fis_l.close();
                    fis_b.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        date.setText( dateAndTime );
        battery.setText( batteryS );
        location.setText( locationS );
        recordPlay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!playing)
                {
                    playing = true;
                    recordPlay.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_pause,null ) );
                    setPlay( playing );
                }
                else
                {
                    playing = false;
                    recordPlay.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_play,null ) );
                    setPlay( playing );
                }

            }
        } );
        return view;
    }

    public void setPlay(boolean mode) throws IllegalArgumentException,
            SecurityException, IllegalStateException
    {
        if(mode)
        {
            String aud = null;
            mediaPlayer = new MediaPlayer();
            try {
                fis_m = context.openFileInput( audioFileName );
                InputStreamReader isr = new InputStreamReader( fis_m );
                BufferedReader br = new BufferedReader( isr );
                StringBuilder sb = new StringBuilder(  );
                while((aud=br.readLine())!=null)
                {
                    sb.append( aud );
                }
                if(sb!=null)
                {
                    aud = sb.toString();
                    mediaPlayer.setDataSource( aud );
                    mediaPlayer.prepare();
                }
                else
                {
                    Toast.makeText( context, "No Recordings Found!", Toast.LENGTH_LONG ).show();
                }

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

            }
        }
    }
    public void onAttach(Context context) {

        super.onAttach( context );
        this.context = context;
    }
}

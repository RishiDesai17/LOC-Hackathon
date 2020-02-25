package com.example.lineofsafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

public class MyDatabase {

    String username = "Name.txt";
    String userblood = "Blood.txt";
    String userdob = "Birth.txt";
    String usergender = "Gender.txt";
    String usermedical = "Medical.txt";
    String userphone1 = "Phone1.txt";
    String userphone2 = "Phone2.txt";
    String userphone3 = "Phone3.txt";

    public int insertData(String name, String dob,String gender,String blood_grp,String medical,String p1,String p2,String p3,Context cont)
    {
        FileOutputStream fos_n = null;
        FileOutputStream fos_b = null;
        FileOutputStream fos_d = null;
        FileOutputStream fos_g = null;
        FileOutputStream fos_m = null;
        FileOutputStream fos_1 = null;
        FileOutputStream fos_2 = null;
        FileOutputStream fos_3 = null;
        int id = 0;
        try {
            fos_n = cont.openFileOutput(username, MODE_PRIVATE);
            fos_b = cont.openFileOutput(userblood, MODE_PRIVATE);
            fos_d = cont.openFileOutput(userdob, MODE_PRIVATE);
            fos_g = cont.openFileOutput(usergender, MODE_PRIVATE);
            fos_m = cont.openFileOutput(usermedical, MODE_PRIVATE);
            fos_1 = cont.openFileOutput(userphone1, MODE_PRIVATE);
            fos_2 = cont.openFileOutput(userphone2, MODE_PRIVATE);
            fos_3 = cont.openFileOutput(userphone3, MODE_PRIVATE);

            fos_n.write( name.getBytes() );
            fos_b.write( blood_grp.getBytes() );
            fos_d.write( dob.getBytes() );
            fos_g.write( gender.getBytes() );
            fos_m.write( medical.getBytes() );
            fos_1.write( p1.getBytes() );
            fos_2.write( p2.getBytes() );
            fos_3.write( p3.getBytes() );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos_n!=null && fos_b!=null && fos_d!=null && fos_g!=null && fos_m!=null && fos_1!=null && fos_2!=null && fos_3!=null)
            {
                try {
                    fos_n.close();
                    fos_b.close();
                    fos_d.close();
                    fos_g.close();
                    fos_m.close();
                    fos_1.close();
                    fos_2.close();
                    fos_3.close();
                    id = 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                id = -1;
            }
        }
        return id;
    }



    public String getName(Context context)
    {
        FileInputStream fis = null;
        String name1 = "";
        try {
            fis = context.openFileInput( username );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            if((name1 = br.readLine())!=null)
            {
                sb.append(name1);
            }
            name1 = sb.toString();
            Log.d("NAME: ",name1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(name1 == "" || name1 == null )
        {
            return "Error!";
        }
        else {
            return name1;
        }
    }
    public String getBlood(Context context)
    {
        FileInputStream fis = null;
        String blood1 = "";
        try {
            fis = context.openFileInput( userblood );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            if((blood1 = br.readLine())!=null)
            {
                sb.append(blood1);
            }
            blood1 = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(blood1 == "" )
        {
            return "Error!";
        }
        else {
            return blood1;
        }
    }
    public String getGender(Context context)
    {
        FileInputStream fis = null;
        String gender1 = "";
        try {
            fis = context.openFileInput( usergender );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );

            if((gender1 = br.readLine())!=null)
            {
                sb.append(gender1);
            }
            gender1 = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(gender1== "" )
        {
            return "Error!";
        }
        else {
            return gender1;
        }
    }
    public String getDob(Context context)
    {
        FileInputStream fis = null;
        String dob1 = "";
        try {
            fis = context.openFileInput( userdob );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            if((dob1 = br.readLine())!=null)
            {
                sb.append( dob1);
            }
            dob1 = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(dob1== "" )
        {
            return "Error!";
        }
        else {
            return dob1;
        }

    }
    public String getMed(Context context)
    {
        FileInputStream fis = null;
        String mednotes = "";
        try {
            fis = context.openFileInput( usermedical );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            while((mednotes = br.readLine())!=null)
            {
                sb.append(mednotes);
            }
            mednotes = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(mednotes== "" )
        {
            return "Error!";
        }
        else {
            return mednotes;
        }
    }
    public String getPhone1(Context context)
    {
        FileInputStream fis = null;
        String phone1 = "";
        try {
            fis = context.openFileInput( userphone1 );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            while((phone1 = br.readLine())!=null)
            {
                sb.append(phone1);
            }
            phone1 = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(phone1== "" )
        {
            return "Error!";
        }
        else {
            return phone1;
        }
    }
    public String getPhone2(Context context)
    {
        FileInputStream fis = null;
        String phone1 = "";
        try {
            fis = context.openFileInput( userphone2 );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            while((phone1 = br.readLine())!=null)
            {
                sb.append(phone1);
            }
            phone1 = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(phone1== "" )
        {
            return "Error!";
        }
        else {
            return phone1;
        }
    }
    public String getPhone3(Context context)
    {
        FileInputStream fis = null;
        String phone1 = "";
        try {
            fis = context.openFileInput( userphone3 );
            InputStreamReader isr = new InputStreamReader( fis );
            BufferedReader br = new BufferedReader( isr );
            StringBuilder sb = new StringBuilder(  );
            while((phone1 = br.readLine())!=null)
            {
                sb.append(phone1);
            }
            phone1 = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(phone1== "" )
        {
            return "Error!";
        }
        else {
            return phone1;
        }
    }


}

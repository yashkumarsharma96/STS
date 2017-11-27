package shatarupa.sts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import java.util.Calendar;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Requesting permission to Send SMS
        {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) //Checking if permission to send SMS has been granted on not. If negative, the following code is executed
            {
                    ActivityCompat.requestPermissions(this, //Requesting permission to send SMS
                            new String[]{Manifest.permission.SEND_SMS},
                            0);
            }
        }
        setContentView(R.layout.activity_home); //Setting layout for the activity
        Toolbar toolbar = findViewById(R.id.toolbar); //Toolbar initialisation
        setSupportActionBar(toolbar);
        final Context context = this;
        FloatingActionButton fab = findViewById(R.id.fab); //Floating action button to add and event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, addEvent.class); //switching to addevent activity if (+) button is pressed
                startActivity(intent);
            }
        });
        TextView headingText, bodyText;
        headingText = findViewById(R.id.heading);
        bodyText = findViewById(R.id.textbody);
        SharedPreferences settings = context.getSharedPreferences("pref_settings", Context.MODE_PRIVATE); //Shared preferences allow us to store simple default variables like int, float, long, boolean in memory (on
        //internal storage/sd card etc. A new shared pref settings have been created with name pref_settings
        int count=settings.getInt("Count",0); //Retreiving int values from shared preferences
        //displaying text in textviews
        if(count==0)
        {
         headingText.setText(R.string.home_no_event); //if count=0, that means no events have been set till now, and hence this text
        }
        else
        {
            headingText.setText(R.string.home_event_added_heading); //if some events are set, the follwing text is displayed.
        }
        int i;
        for(i=0;i<count;i++)
        {
            String name = settings.getString("Name" + i, ""); //Retreiving name from saved data
            Boolean isBirthday = settings.getBoolean("Birthday" + i, true); //Retreiving event type (birthday/anniversary) from saved data
            long timeRetrieved = settings.getLong("Time" + i, 0); //Retreviing time from saved data
            Calendar currentCalendar= Calendar.getInstance();
            long currentTime=currentCalendar.getTimeInMillis(); //Getting the current time
            if(timeRetrieved>=currentTime) {
                Calendar calendar = Calendar.getInstance(); //Getting calendar
                calendar.setTimeInMillis(timeRetrieved); //Setting the time retreived from saved data (at the time of form filling)
                String occassion;
                if (isBirthday) {
                    occassion = "Birthday";
                } else {
                    occassion = "Anniversary";
                }
                int day, month, year;
                day = calendar.get(Calendar.DAY_OF_MONTH); //Getting date from the time in milliseconds by using the calendar
                month = calendar.get(Calendar.MONTH)+1; //Getting month from the time in ms by using the calendar
                year = calendar.get(Calendar.YEAR); //Gettng the year from the time in ms by using the calendar
                bodyText.append("" + (i+1) + ". " + name + "\n\t\t" + occassion + "\n\t\t" + day + "-" + month + "-" + year + "\n\n");  //Normal string operations in this loop, based on birthday/anniversary
            }
        }
    }
}
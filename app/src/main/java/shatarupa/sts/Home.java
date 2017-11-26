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
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},
                            0);
            }
        }
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Switching to the activity to add an event (Birthday/Anniversary)
                Intent intent = new Intent(context, addEvent.class);
                startActivity(intent);
                finish();
            }
        });
        TextView headingText, bodyText;
        headingText = (TextView) findViewById(R.id.heading);
        bodyText = (TextView) findViewById(R.id.textbody);
        SharedPreferences settings = context.getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
        int count=settings.getInt("Count",0);
        //displaying text in textviews
        if(count==0)
        {
         headingText.setText(R.string.home_no_event);
        }
        else
        {
            headingText.setText(R.string.home_event_added_heading);
        }
        int i;
        for(i=0;i<count;i++)
        {
            String name = settings.getString("Name" + i, "");
            Boolean isBirthday = settings.getBoolean("Birthday" + i, true);
            long timeRetrieved = settings.getLong("Time" + i, 0);
            Calendar currentCalendar= Calendar.getInstance();
            long currentTime=currentCalendar.getTimeInMillis();
            if(timeRetrieved>=currentTime) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeRetrieved);
                String occassion = "";
                if (isBirthday) {
                    occassion = "Birthday";
                } else {
                    occassion = "Anniversary";
                }
                int day = 0;
                int month = 0;
                int year = 0;
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH)+1;
                year = calendar.get(Calendar.YEAR);
                bodyText.append("" + (i+1) + ". " + name + "\n\t\t" + occassion + "\n\t\t" + day + "-" + month + "-" + year + "\n\n");
            }
        }
    }
}
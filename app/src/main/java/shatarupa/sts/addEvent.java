package shatarupa.sts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import android.view.inputmethod.InputMethodManager;

public class addEvent extends AppCompatActivity {
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus(); //Getting current focus to the view
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
    //Code to hide the keyboard when not required
    Button saveButton;
    TextView nameTextbox, phoneTextbox;
    String name;
    DatePicker datePicker;
    RadioButton radioBirthday;
    int day, month, year, count, hour, minute;
    String phoneNumber;
    long time;
    Context context;
    boolean isBirthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        count=0;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        {
            SharedPreferences settings = getSharedPreferences("pref_settings", MODE_PRIVATE);
            count=settings.getInt("Count",0);
            context=getApplicationContext();
            saveButton = findViewById(R.id.saveButton1);
            nameTextbox = findViewById(R.id.nameEnter);
            phoneTextbox = findViewById(R.id.phoneNumber);
            datePicker = findViewById(R.id.datePicker);
            radioBirthday = findViewById(R.id.buttonBirthday);
            hour=minute=0;
            final TimePicker timePicker = findViewById(R.id.timePicker);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //doing things when save button is pressed
                    name=null;
                    phoneNumber=phoneTextbox.getText().toString();
                    name = nameTextbox.getText().toString();
                    if(phoneNumber.length()<10 || phoneNumber.length()>10) //what to do if phone no. is less/greater than 10 digits
                    {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT; //during of small message
                        Toast toast = Toast.makeText(context, R.string.phone_error, duration); //toasts are default small messages. we are showing the error using a simle toast
                        toast.show();
                    }
                    else if (name.equals("")){ //what to do if the name is blank
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, R.string.name_error, duration);
                        toast.show();
                    }
                    else { //What to do if name and phone number both are correct
                        day = datePicker.getDayOfMonth(); //getting date from datepicker
                        month = datePicker.getMonth(); //getting month from datepicker
                        year = datePicker.getYear(); //getting year from datepicker
                        if(Build.VERSION.SDK_INT < 23 ){
                            hour=timePicker.getCurrentHour(); //getting hour
                            minute=timePicker.getCurrentMinute(); //getting minute
                        }
                        else{
                            hour=timePicker.getHour();
                            minute=timePicker.getMinute();
                        }
                        isBirthday = radioBirthday.isChecked(); //determining what the user has pressed by analysing radiobuttons
                        Calendar calendar = Calendar.getInstance(); //Another Calendar
                        calendar.set(year, month, day, hour, minute, 1);
                        time = calendar.getTimeInMillis();
                        setAlarm(time); //Setting a system alarm to be fired on the date+time of event
                        save(name, time, isBirthday, phoneNumber); //refer below
                    }
                    goBack();
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    void setAlarm(long time)
    {
        Intent intentAlarm = new Intent(this, AlarmReceiver.class); //creating intent
        AlarmManager fireSMS = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //using android alarm manager
        final int uniqueNumber = (int) System.currentTimeMillis(); //unique number for id of Alarm
        fireSMS.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, uniqueNumber, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)); //Setting up the alarm, alarmID, intent, flag
    }
    void goBack(){
        Intent intent = new Intent(context, Home.class); //going back after saving the data
        startActivity(intent);
        finish(); //killing the activity as it is no longer required. generally the last activity in a tree is killed like this, to avoid issues with the back key
    }
    void save (String name, long time, boolean isBirthday, String phoneNumber){
        SharedPreferences settings = getSharedPreferences("pref_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Name"+count,name);
        editor.putString("PhoneNumber"+count,phoneNumber);
        editor.putLong("Time"+count,time);
        editor.putBoolean("Birthday"+count,isBirthday);
        count++;
        editor.putInt("Count",count); //putting the different values in the sharedpreferences, to be retreived later
        editor.apply(); //actual save to the disk/internal storage
    }
}
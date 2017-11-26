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
        View v = getCurrentFocus();
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        {
            SharedPreferences settings = getSharedPreferences("pref_settings", MODE_PRIVATE);
            count=settings.getInt("Count",0);
            context=getApplicationContext();
            saveButton = (Button) findViewById(R.id.saveButton1);
            nameTextbox = (TextView) findViewById(R.id.nameEnter);
            phoneTextbox = (TextView) findViewById(R.id.phoneNumber);
            datePicker = (DatePicker) findViewById(R.id.datePicker);
            radioBirthday = (RadioButton) findViewById(R.id.buttonBirthday);
            hour=minute=0;
            final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name=null;
                    phoneNumber=phoneTextbox.getText().toString();
                    name = nameTextbox.getText().toString();
                    if(phoneNumber.length()<10)
                    {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, R.string.phone_error, duration);
                        toast.show();
                    }
                    else if (name.equals("")){
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, R.string.name_error, duration);
                        toast.show();
                    }
                    else {
                        day = datePicker.getDayOfMonth();
                        month = datePicker.getMonth();
                        year = datePicker.getYear();
                        if(Build.VERSION.SDK_INT < 23 ){
                            hour=timePicker.getCurrentHour();
                            minute=timePicker.getCurrentMinute();
                        }
                        else{
                            hour=timePicker.getHour();
                            minute=timePicker.getMinute();
                        }
                        isBirthday = radioBirthday.isChecked();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hour, minute, 1);
                        time = calendar.getTimeInMillis();
                        setAlarm(time);
                        save(name, time, isBirthday, phoneNumber);
                    }
                    goBack();
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    void setAlarm(long time)
    {
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        AlarmManager fireSMS = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final int uniqueNumber = (int) System.currentTimeMillis();
        fireSMS.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, uniqueNumber, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }
    void goBack(){
        Intent intent = new Intent(context, Home.class);
        startActivity(intent);
        finish();
    }
    void save (String name, long time, boolean isBirthday, String phoneNumber){
        SharedPreferences settings = getSharedPreferences("pref_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Name"+count,name);
        editor.putString("PhoneNumber"+count,phoneNumber);
        editor.putLong("Time"+count,time);
        editor.putBoolean("Birthday"+count,isBirthday);
        count++;
        editor.putInt("Count",count);
        editor.apply();
    }
}
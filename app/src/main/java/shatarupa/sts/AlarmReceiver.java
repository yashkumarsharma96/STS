package shatarupa.sts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.telephony.SmsManager;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver{
    long time;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE); //Accessing powermanager
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tag"); //defining wakelock
        wl.acquire(); //acquiring wakelock
        //wakelock helps to ensure that cpu is waked up even if the device is sleeping. Android is responsible for making cpu available for our app & we dont have to do anything
        SharedPreferences settings = context.getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
        Calendar calendar= Calendar.getInstance();
        int count=settings.getInt("Count",0);
        Calendar current=Calendar.getInstance();
        int currentDay=current.get(Calendar.DAY_OF_MONTH);
        time=0;
        int i;
        for(i=0;i<count;i++) {
            time = settings.getLong("Time" + i, 0); //getting stored time
                calendar.setTimeInMillis(time); //setting the retrevied time in calendar
                int planDay = calendar.get(Calendar.DAY_OF_MONTH); //getting the event day
                if (currentDay == planDay) { //if today = event day ,then, the following code is executed
                    String name = settings.getString("Name" + i, ""); //getting all details about the birthday/anniversary
                    String phoneNumber = settings.getString("PhoneNumber" + i, "");
                    Boolean isBirthday = settings.getBoolean("Birthday" + i, true);
                    SmsManager smsManager = SmsManager.getDefault();
                    String wish;
                    String Event="Birthday ";
                    String Variable=".";
                    if(!isBirthday)
                    {
                        Event="Anniversary ";
                        Variable=" both.";
                    }
                    wish="Happy "+Event+name+" !! May God bless you"+Variable; //wish to be sent via the sms
                    smsManager.sendTextMessage(phoneNumber, null, wish, null, null); //sending an SMS method= phonenumber, null, text to send, null, null
                }
        }
        wl.release(); //Releasing the wakelock. Important so that app does not continue to use the cpu and drain the battery super fast.
    }
}

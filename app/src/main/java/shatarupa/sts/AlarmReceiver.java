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
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tag");
        wl.acquire();
        SharedPreferences settings = context.getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
        Calendar calendar= Calendar.getInstance();
        int count=settings.getInt("Count",0);
        Calendar current=Calendar.getInstance();
        int currentDay=current.get(Calendar.DAY_OF_MONTH);
        time=0;
        int i;
        for(i=0;i<count;i++) {
            time = settings.getLong("Time" + i, 0);
                calendar.setTimeInMillis(time);
                int planDay = calendar.get(Calendar.DAY_OF_MONTH);
                if (currentDay == planDay) {
                    String name = settings.getString("Name" + i, "");
                    String phoneNumber = settings.getString("PhoneNumber" + i, "");
                    Boolean isBirthday = settings.getBoolean("Birthday" + i, true);
                    SmsManager smsManager = SmsManager.getDefault();
                    String wish=null;
                    String Event="Birthday ";
                    String Variable=".";
                    if(!isBirthday)
                    {
                        Event="Anniversary ";
                        Variable=" both.";
                    }
                    wish="Happy "+Event+name+" !! May God bless you"+Variable;
                    smsManager.sendTextMessage(phoneNumber, null, wish, null, null);
                }
        }
        wl.release();
    }
}

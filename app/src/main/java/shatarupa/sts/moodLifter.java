package shatarupa.sts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class moodLifter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_lifter);
        final Context context=this;
        Button sad;
        sad = (Button) findViewById(R.id.b31);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moodLifterResult.class);
                intent.putExtra("Mood",true);
                startActivity(intent);
            }
        });
        Button angry;
        angry = (Button) findViewById(R.id.b32);
        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moodLifterResult.class);
                intent.putExtra("Mood",false);
                startActivity(intent);
            }
        });
    }
}
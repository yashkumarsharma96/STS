package shatarupa.sts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);
        final Context context=this;
        Button moodLifterButton;
        moodLifterButton = (Button) findViewById(R.id.b21);
        moodLifterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moodLifter.class);
                startActivity(intent);
            }
        });
        Button autoMessageButton;
        autoMessageButton = (Button) findViewById(R.id.b22);
        autoMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                startActivity(intent);
            }
        });
    }
}

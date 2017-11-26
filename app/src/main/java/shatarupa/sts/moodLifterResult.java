package shatarupa.sts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class moodLifterResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_lifter_result);
        Intent myIntent = this.getIntent();
        boolean type=myIntent.getBooleanExtra("Mood", true);
        TextView textbox;
        textbox = (TextView) findViewById(R.id.txt1);
        String textToDisplay;
        String packageName = getPackageName();
        int i=0;
        if(type)
        {
            String jokes=getString(R.string.jokes_heading);
            String currentjoke;
            int numberOfJokes=5;
            for(i=0;i<numberOfJokes;i++) {
                int jokeId = getResources().getIdentifier("joke" + i, "string", packageName);
                currentjoke = getString(jokeId);
                jokes=jokes+currentjoke;
            }
            textbox.setText(jokes);
        }
        else
        {
            String tips=getString(R.string.tips_heading);
            String currentjoke;
            int numberOfTips=5;
            for(i=0;i<numberOfTips;i++) {
                int tipId = getResources().getIdentifier("tip" + i, "string", packageName);
                currentjoke = getString(tipId);
                tips=tips+currentjoke;
            }
            textbox.setText(tips);
        }
    }
}
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
        Intent myIntent = this.getIntent(); //We are retreving the intent from which this activity was started. The purpose is to get the additional information that we have put into the intent while starting the
        //activity.
        boolean type=myIntent.getBooleanExtra("Mood", true); //We are retreving the boolean value stored in variable name "Mood" inside the intent.
        TextView textbox; //Defining a textview
        textbox = findViewById(R.id.txt1); //Initialising the textview with its corresponding layout (using id 'txt1')
        String packageName = getPackageName(); //getting the package name
        int i;
        if(type) //if user has selected sad in prior activity
        {
            String jokes=getString(R.string.jokes_heading);
            String currentjoke; //string for current joke
            int numberOfJokes=5; //Number of total jokes
            for(i=0;i<numberOfJokes;i++) {
                int jokeId = getResources().getIdentifier("joke" + i, "string", packageName); //Retreving resourceID strings stored from strings.xml file using names like joke0, joke1, ... using the loop.
                //each resource is assigned a resourceID. we can retreive the resource by using its resource id
                currentjoke = getString(jokeId); //getting the actual string using the resourceID
                jokes=jokes+currentjoke; //Merging the headline with jokes and prior jokes with current joke
            }
            textbox.setText(jokes); //Displaying the text (jokes) in textview using setText method
        }
        else
        {
            String tips=getString(R.string.tips_heading); //Else case if the user has selected angry in previous activity
            String currentjoke;  //string for current tip
            int numberOfTips=5; //Number of total tips
            for(i=0;i<numberOfTips;i++) {
                int tipId = getResources().getIdentifier("tip" + i, "string", packageName); //Retreving resourceID strings stored from strings.xml file using names like tip0, tip1, ... using the loop.
                //each resource is assigned a resourceID. we can retreive the resource by using its resource id
                currentjoke = getString(tipId); //getting actual string using resourceID
                tips=tips+currentjoke; //Merging
            }
            textbox.setText(tips); //Dispaying tips in textview.
        }
    }
}
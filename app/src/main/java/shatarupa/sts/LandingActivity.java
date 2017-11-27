package shatarupa.sts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {   //Whenever any activity starts, the first method that is called by the android is the onCreate Method. As a matter of general practice, we tend to put out
        //basic code in this method only, and refer to other methods from within this method. It is can be considered as the 'int main ()' method that we use in C++.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);  //We specify the layout of the current activity, i.e., how different widgets/objects etc (like buttons, textviews, imageviews etc) would be displayed within the activity. The layout & styling structure
        //is saved inside an xml file. XML stands for Extensible Markup language.
        final Context context=this;  //We define the context in which the activity is operating. It lets newly-created objects understand what has been going on. Context is can be compared to the environment in which
        //out activity is running
        Button moodLifterButton; //Defining a button
        moodLifterButton = findViewById(R.id.b21); //Assigning out button the settings we have defined in our xml file for that particular button using the 'Android id (b21)' It is can be thought of as the initialisation of
        // a button. Like we initialis int i=0; i.e., assign a value.
        moodLifterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //Defining what happens when the button is clicked
                Intent intent = new Intent(context, moodLifter.class); //Intent is can be thought of as a abstract class, which contains the information about how a particular activity/service etc is to be started.
                //We define a new Intent named 'intent' and used the new Intent method. The context (environment in which the intent is initialised is context (defined above) and the target activity is moodLifter.class
                startActivity(intent); //We stated the activity with the information contained in 'intent' (Defined above)
            }
        });
        Button autoMessageButton; //Defining a button
        autoMessageButton = findViewById(R.id.b22); //Assigning out button the settings we have defined in our xml file for that particular button using the 'Android id (b22)' It is can be thought of as the initialisation of
        // a button. Like we initialis int i=0; i.e., assign a value.
        autoMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Defining what happens when the button is clicked
                Intent intent = new Intent(context, Home.class); //Intent is can be thought of as a abstract class, which contains the information about how a particular activity/service etc is to be started.
                //We define a new Intent named 'intent' and used the new Intent method. The context (environment in which the intent is initialised is context (defined above) and the target activity is Home.class
                startActivity(intent); //We stated the activity with the information contained in 'intent' (Defined above)
            }
        });
    }
}

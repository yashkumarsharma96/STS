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
        sad = findViewById(R.id.b31);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moodLifterResult.class);
                intent.putExtra("Mood",true); //We are putting in an extra information in the intent, i.e., a boolean value, which is can be retreived in the next activity. Putting values in intent is a
                //simple way to transfer variables like int float, boolean from one activity to another. we give a name to the value, using which would retreive the value in the next activity and assign a value.
                //We put the value of "Mood" as true if the user clicks the sad button. So in the next activity if the value retreived is true, we know that we have to do something (like display jokes) to make the
                //user happy.
                startActivity(intent);
            }
        });
        Button angry;
        angry = findViewById(R.id.b32);
        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moodLifterResult.class);
                intent.putExtra("Mood",false); //We are putting in an extra information in the intent, i.e., a boolean value, which is can be retreived in the next activity. Putting values in intent is a
                //simple way to transfer variables like int float, boolean from one activity to another. we give a name to the value (like int 'i'), using which would retreive the value in the next activity and
                // assign a value. We put the value of "Mood" as true if the user clicks the angry button. So in the next activity if the value retreived is false, we know that we have to do something (like
                // display tips) to calm the user happy.
                startActivity(intent);
            }
        });
    }
}
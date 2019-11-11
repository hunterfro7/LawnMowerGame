package one.group.oneapp;

import android.app.Activity;
import android.view.View;
import android.widget.Chronometer;

import static android.view.View.VISIBLE;

public class Stopwatch extends Activity {

    private Chronometer timeView;

    public Stopwatch() {

    }

    public void makeVisible(){
        timeView = findViewById(R.id.timeView);

        timeView.setVisibility(VISIBLE);
    }

}
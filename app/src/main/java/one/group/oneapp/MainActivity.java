package one.group.oneapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


    }
    public void clickPlay(View view){
        Intent intent = new Intent(MainActivity.this,InGame.class);
        startActivity(intent);
    }

    public void clickReset(View view){
        File dir = getFilesDir();
        File file = new File(dir, "game.data");
        boolean deleted = file.delete();
        TextView reset = (TextView) view;
        if(deleted) reset.setText(getString(R.string.deleted));
    }
    public void testDelete(){
        File dir = getFilesDir();
        File file = new File(dir, "game.data");
        file.delete();
    }
}

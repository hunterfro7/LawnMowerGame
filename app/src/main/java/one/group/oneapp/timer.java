package one.group.oneapp;

import android.view.View;
import android.widget.TextView;

public class timer extends upgradeStats {

    private int level = 0;
    public int getCost(){
        return 30;
    }

    public int getLevel(){
        level++;
        return level;
    }

    public boolean buy(){
        return true;
    }
}

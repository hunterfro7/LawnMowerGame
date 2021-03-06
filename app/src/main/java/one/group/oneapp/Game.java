package one.group.oneapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.io.Serializable;

public class Game implements Serializable {
    private HarvestableManager harvestableManager;
    private Player player;
    private int frame;

    public  Game(){
        harvestableManager = new HarvestableManager();
        player = new Player();
        this.frame = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void update(){
        this.frame++;
        player.move();
        harvestableManager.collidePlayer(player);

    }
    public void doTouch(int touchx,int touchy){
        int xDiff = Math.abs(touchx - player.getX());
        int yDiff = Math.abs(touchy - player.getY());
        if (xDiff > yDiff) {
            if (touchx > player.getX()) {
                player.setDirection(Player.Directions.RIGHT);
            } else {
                player.setDirection(Player.Directions.LEFT);
            }
        } else {
            if (touchy > player.getY()) {
                player.setDirection(Player.Directions.DOWN);
            } else {
                player.setDirection(Player.Directions.UP);
            }
        }
    }
    public HarvestableManager getHarvestableManager() {
        return harvestableManager;
    }
    public void draw(Canvas c) {
        if(c == null) return;
        c.drawColor(Color.BLACK);
        //c.drawBitmap(alien, null, myRect, black);
        Paint brush = new Paint();
        brush.setColor(Color.rgb(0xff,0xf0,0xff));
        //brush.setStyle(Paint.Style.STROKE);
        c.drawRect(player.getX(),player.getY(),player.getX()+player.getWidth(),player.getY()+player.getHeight(),brush);
        brush.setColor(Color.rgb(0x00,0xff,0x00));
        for(Collidable item: harvestableManager.getPlants()) {
            c.drawRect(item.getX(),item.getY(),item.getX()+item.getWidth(),item.getY()+item.getHeight(),brush);

        }

    }

    public int getFrame() {
        return frame;
    }
}

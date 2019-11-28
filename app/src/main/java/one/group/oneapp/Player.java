package one.group.oneapp;

//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.widget.ImageView;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Player implements Collidable, Serializable //GestureDetector.OnGestureListener
{
//    //No clue what the GestureDetector line does
//    private GestureDetector gestureDetector = new GestureDetector(this);
//    ImageView testImage;
//
//    //Image Dimensions
//    int imageX;
//    int imageY;
//
//    //Screen Dimensions
//    private int screenWidth;
//    private int screenHeight;

    private int x,y,width,height;

    private static final int BASE_STACK = 5;

    private boolean frozen, autoMoving;

    private Wallet wallet;
    private ArrayList<Item> items;
    private double speed;
    private Directions direction;
    private UpgradeManager upgradeManager;
    public enum Directions{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    public Player(){
        this.x = 250;
        this.y = 700;
        this.height = 100;
        this.width = 100;
        this.speed = 10;
        this.items = new ArrayList<Item>();
        this.items.add(new GrassItem());
        this.wallet = new Wallet();
        this.direction = Directions.LEFT;
        this.upgradeManager = new UpgradeManager();
        this.autoMoving = false;
    }

    public void clearItems(){
        this.items = new ArrayList<Item>();
        this.items.add(new GrassItem());
    }

    public void move(){
        if(frozen) return;
        if(this.autoMoving && Math.random() < 0.02){
            this.doAutoMove();
        }
        switch(this.direction){
            case LEFT:
                this.x += this.getSpeed() * -1;
                break;
            case RIGHT:
                this.x += this.getSpeed();
                break;
            case UP:
                this.y += this.getSpeed() * -1;
                break;
            case DOWN:
                this.y += this.getSpeed();
                break;
        }
        Boolean hitWall = false;
        if(this.x < PhysicsManager.minX){
            this.x = PhysicsManager.minX;
            hitWall = true;
        }
        if(this.y < PhysicsManager.minY){
            this.y = PhysicsManager.minY;
            hitWall = true;
        }
        if(this.x + this.getWidth() > PhysicsManager.maxX){
            this.x = PhysicsManager.maxX - this.getWidth();
            hitWall = true;
        }
        if(this.y + this.getHeight() > PhysicsManager.maxY){
            this.y = PhysicsManager.maxY - this.getHeight();
            hitWall = true;
        }
        if(this.autoMoving && hitWall){
            this.doAutoMove();
        }
    }

    public boolean canAutoMove(){
        return upgradeManager.getMoveUpgrade().getLevel() != 0;
    }

    void doAutoMove() {
        this.direction = Directions.values()[(int) Math.floor(Math.random() * Directions.values().length)];
    }
    public boolean isAutoMoving() {
        return autoMoving;
    }

    public boolean toggleAutoMoving(){
        return this.autoMoving = !this.autoMoving;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return (int)Math.round(width + width * this.upgradeManager.getSizeUpgrade().getBonus());
    }

    public int getHeight() {
        return (int)Math.round(height + height * this.upgradeManager.getSizeUpgrade().getBonus());
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setUpgradeManager(UpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
    }

    public Directions getDirection() {
        return direction;
    }

    public int getItems() {
        return items.get(0).getCount();
    }

    public double getSpeed() {
        return speed + speed * this.upgradeManager.getSpeedUpgrade().getBonus();

    }

    public int getMoney() {
        return wallet.getMoney();
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void incrementItems(){
        items.get(0).increaseCount(1);
    }

    public void sell(){
        items.get(0).sell(wallet,this.getMaxStack(),this.getSellMultiplier());
    }

    public UpgradeManager getUpgradeManager() {
        return upgradeManager;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public int getMaxStack(){
        /*For selling, not for items*/
        return (int)(BASE_STACK + Math.round(BASE_STACK * (this.upgradeManager.getSalesUpgrade().getBonus() * 5)));
    }
    public double getSellMultiplier(){
        return this.upgradeManager.getSalesUpgrade().getBonus() + 1;
    }

//    //Methods needed by GestureDetector
//    @Override
//    public boolean onDown(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent pressDown, MotionEvent moveFinger, float xAxis, float yAxis)
//    {
//        float xDistance = moveFinger.getX() - pressDown.getX(); //Distance of swipe in X direction
//        float yDistance = moveFinger.getY() - pressDown.getY(); //Distance of swipe in Y direction
//
//        if(Math.abs(xDistance) > Math.abs(yDistance)) //If swiped farther in X direction then Y
//        {
//            if(Math.abs(xDistance) > 50 && Math.abs(xAxis) > 50)//Threshold for how far user has to swipe and how fast
//            {
//                if(xDistance > 0) //Swiped right
//                {
//                    moveImage("Right");
//                    return true;
//                }
//                else //Swiped left
//                {
//                    moveImage("Left");
//                    return true;
//                }
//            }
//        }
//        else
//        {
//            if(Math.abs(yDistance) > 50 && Math.abs(yAxis) > 50)
//            {
//                if(yDistance < 0) //Swiped up, Up is considered the negative direction????
//                {
//                    moveImage("Up");
//                    return true;
//                }
//                else //Swiped down
//                {
//                    moveImage("Down");
//                    return true;
//                }
//            }
//        }
//
//        return false;//Only reached if user didnt swipe correctly
//    }
//
//    //Moves image 100 pixels in swiped Direction
//    private void moveImage(String Direction)
//    {
//        switch(Direction)
//        {
//            case "Up":
//            {
//                imageY = imageY - 100;
//
//                if(imageY < 0)
//                {
//                    imageY = 0;
//                    testImage.setY(imageY);
//                }
//                else
//                {
//                    testImage.setY(imageY);
//                }
//
//                break;
//            }
//            case "Down":
//            {
//                imageY = imageY + 100;
//
//                if(imageY > screenHeight)//If move will put past ceiling, just move picture to wall
//                {
//                    imageY = screenHeight;
//                    testImage.setY(imageY);
//                }
//                else
//                {
//                    testImage.setY(imageY);
//                }
//
//                break;
//            }
//            case "Left":
//            {
//                imageX = imageX - 100;
//
//                if(imageX < 0)
//                {
//                    imageX = 0;
//                    testImage.setX(imageX);
//                }
//                else
//                {
//                    testImage.setX(imageX);
//                }
//
//                break;
//            }
//            case "Right":
//            {
//                imageX = imageX + 100;
//
//                if(imageX > screenWidth)
//                {
//                    imageX = screenWidth;
//                    testImage.setX(imageX);
//                }
//                else
//                {
//                    testImage.setX(imageX);
//                }
//
//                break;
//            }
//        }
//
//        System.out.println("Direction moved: " + Direction + " x coordinates: " + imageX + " y coordinates: " + imageY);
//    }


}

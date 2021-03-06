package one.group.oneapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class InGame extends Activity implements SurfaceHolder.Callback {
    private final int UPGRADE_MENU = 1;
    private myThread thread;
    public Paint black;
    private TextView sellView;
    private TextView moneyView;
    private TextView autoMoveView;

    private int height = 480, width = 480;  //defaults incase not set yet.
    float scale;
    private Game game;
    public Context context;

    SurfaceView mSurfaceView;
    String TAG = "InGame";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_in_game);
        //setup everything needed.
        scale = getResources().getDisplayMetrics().density; //this gives me the scale value for a mdpi baseline of 1.

        black = new Paint();  //default is black and we really are not using it.  need it to draw the alien.

        sellView = ((TextView)findViewById(R.id.sell));
        moneyView = ((TextView)findViewById(R.id.money));
        autoMoveView = ((TextView)findViewById(R.id.automove));
        this.context = getApplicationContext();
        game = new Game();
        loadGame();


        //get a generic surface and all our callbacks to it, with a touchlistener.
        mSurfaceView = findViewById(R.id.surfaceView); //new SurfaceView(this);
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                // Retrieve the new x and y touch positions
                int touchx = (int) event.getX();
                int touchy = (int) event.getY();
                game.doTouch(touchx,touchy);
                v.performClick();
                return true;
            }

        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(thread != null) thread.interrupt();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(thread != null) thread.run();
    }

    public void clickUpgrades(View view){
        Intent intent = new Intent(InGame.this,UpgradeMenu.class);
        intent.putExtra("upgradeManager",game.getPlayer().getUpgradeManager());
        intent.putExtra("wallet",game.getPlayer().getWallet());
        startActivityForResult(intent, UPGRADE_MENU);


    }

    public void deleteSave(){
        File dir = getFilesDir();
        File file = new File(dir, "game.data");
        file.delete();
    }

    private void saveGame() {
        try{
            FileOutputStream fos = this.context.getApplicationContext().openFileOutput("game.data", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(game);
            os.close();
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void loadGame(){
        try{
            FileInputStream fis = getApplicationContext().openFileInput("game.data");
            ObjectInputStream is = new ObjectInputStream(fis);
            Game game = (Game) is.readObject();
            is.close();
            fis.close();
            this.game = game;
        }catch (Exception e){
            Log.e("#Load", "creating new game - \n" + e.toString());
        }
    }

    public void clickSell(View view){
        //View text = findViewById(R.id.sell);
        //((TextView) text).setText("Sell some of " + game.getPlayer().sell() + " items");
        game.getPlayer().sell();
    }

    public void clickAutoMove(View view){
        if(getPlayer().canAutoMove()){
            getPlayer().toggleAutoMoving();
        }
    }

    public void updateViews(){
        sellView.setText(getString(R.string.sellText,game.getPlayer().getItems()));
        moneyView.setText(getString(R.string.dollarMoney,  game.getPlayer().getMoney()));
        String autoMoveText = "Automove: ";
        if(getPlayer().canAutoMove()){
            if(getPlayer().isAutoMoving())
                autoMoveText += "On";
            else
                autoMoveText += "Off";
        }
        else autoMoveText += "Locked";
        autoMoveView.setText(autoMoveText);
    }

    public HarvestableManager getHarvestableManager() {
        return game.getHarvestableManager();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPGRADE_MENU) {
            if (resultCode == RESULT_OK) {
                game.getPlayer().setUpgradeManager((UpgradeManager) data.getSerializableExtra("upgradeManager"));
                game.getPlayer().setWallet((Wallet) data.getSerializableExtra("wallet"));
            }
        }
    }

    public Player getPlayer() {
        return game.getPlayer();
    }



    //all the methods needed for the SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(TAG, "surfaceCreated");
        //everything is setup, now start.
        height = mSurfaceView.getHeight();
        width = mSurfaceView.getWidth();
        //setup the thread for animation.
        thread = new myThread(mSurfaceView.getHolder());
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v(TAG, "surfaceChanged");
        //ignored.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(TAG, "surfaceDestroyed");
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }


    class myThread extends Thread {
        private SurfaceHolder _surfaceHolder;

        private boolean Running = false;

        public myThread(SurfaceHolder surfaceHolder) {
            _surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean run) {


            Running = run;
        }

        @Override
        public void run() {
            Canvas c;
            while (Running && !Thread.interrupted()) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        //call a method that draws all the required objects onto the canvas.
                        game.draw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                //handle the game updates
                game.update();
                if(game.getFrame() % 60 == 0)
                    saveGame();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        updateViews();
                        // Stuff that updates the UI

                    }
                });
                //sleep for a short period of time.
                if (!Running) return;  //don't sleep, just exit if we are done.
                try {
                    Thread.sleep(1000/60);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }
}

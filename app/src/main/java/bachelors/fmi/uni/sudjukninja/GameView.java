package bachelors.fmi.uni.sudjukninja;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{

    boolean isAlive = true;
    Hero player;
    int score = 0;
    int lives = 3;

    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Paint paint;

    Thread gameThread;

    int sizeX;
    int sizeY;

    ArrayList<LilSausage> lilSausages = new ArrayList<>();
    ArrayList<Sausage> sausagesList = new ArrayList<>();

    public GameView(Context context, int sizeX, int sizeY) {
        super(context);

        this.sizeX = sizeX;
        this.sizeY = sizeY;

        player = new Hero(context, sizeX, sizeY);

        for(int i = 0; i < 110; i++){
            lilSausages.add(new LilSausage(context, sizeX, sizeY));
        }

        sausagesList.add(new Sausage(context, sizeX, sizeY));

        surfaceHolder = getHolder();
        paint = new Paint();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (isAlive){
            try {
                gameThread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            draw();
            update();
            score++;

            if(score/700 > sausagesList.size()){
                sausagesList.add(
                        new Sausage(getContext(),
                                sizeX,
                                sizeY));
            }
        }
    }

    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.parseColor("#e1b799"));

            for (LilSausage lil: lilSausages) {
                canvas.drawBitmap(lil.bitmap,
                        lil.x,
                        lil.y,
                        paint);
            }

            for(Sausage sas : sausagesList){
                canvas.drawBitmap(sas.bitmap,
                        sas.x,
                        sas.y,
                        paint);
            }

            canvas.drawBitmap(
                    player.bitmap,
                    player.x,
                    player.y,
                    paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(60);

            canvas.drawText("Lives: " + lives
                    + " Score: " + score,
                    canvas.getWidth()/ 2,
                    300,
                    paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update(){

        for (LilSausage lil: lilSausages) {
            lil.update();
        }

        for (Sausage sas: sausagesList) {
            sas.update();

            if(sas.takeLife){
                lives--;
                sas.takeLife = false;

                if(lives < 0)
                    isAlive = false;
            }

            if(Rect.intersects(
                    sas.detectCollision,
                    player.detectCollision)
                    ){
                score += 10;
                sas.redraw();
            }
        }




    }

    VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionIndex();
        int pointer = event.getPointerId(action);

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:

                if(velocityTracker == null){
                    velocityTracker = VelocityTracker.obtain();
                }else{
                    velocityTracker.clear();
                }

                velocityTracker.addMovement(event);

                break;
            case MotionEvent.ACTION_MOVE:

                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                Log.wtf("Velocity", velocityTracker.getXVelocity() + "");

                if(velocityTracker.getXVelocity() > 0){
                    player.update(15);
                }else if(velocityTracker.getXVelocity() < 0){
                    player.update(-15);
                }

                break;

        }

        return true;

    }
}

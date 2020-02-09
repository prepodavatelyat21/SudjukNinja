package bachelors.fmi.uni.sudjukninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Sausage {
    Context context;
    int maxX;
    int maxY;
    int x;
    int y;
    Bitmap bitmap;

    boolean takeLife = false;

    int speed = 10;

    Rect detectCollision;

    int[] skins = {
            R.drawable.sausage1,
            R.drawable.sausage2,
            R.drawable.sausage3,
            R.drawable.sausage4,
            R.drawable.sausage5
    };

    Random random = new Random();

    public Sausage(Context context, int sizeX, int sizeY){
        this.context = context;
        maxX = sizeX;
        maxY = sizeY;

        bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                skins[random.nextInt(skins.length)]
        );

        detectCollision = new Rect(
                x,
                y,
                x + bitmap.getWidth(),
                y + bitmap.getHeight()
        );
    }

    public void redraw(){
        y = -50;
        x = random.nextInt(maxX) - bitmap.getWidth();
        detectCollision.left = x;
        detectCollision.right = x + bitmap.getWidth();
        speed++;
    }

    public void update(){
        y += speed;

        if(y > maxY){
            redraw();
            takeLife = true;
        }

        detectCollision.top = y;
        detectCollision.bottom = y + bitmap.getHeight();
    }

}

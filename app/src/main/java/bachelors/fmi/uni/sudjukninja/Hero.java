package bachelors.fmi.uni.sudjukninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Hero {

    Context context;
    int maxX;
    int maxY;

    Rect detectCollision;

    int x;
    int y;

    Bitmap bitmap;

    public Hero(Context context, int sizeX, int sizeY){
        this.context = context;

        bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.hero);

        maxX = sizeX - bitmap.getWidth();
        maxY = sizeY - bitmap.getHeight();

        y = maxY - 50;
        x = maxX/2;

        detectCollision = new Rect(
                x,
                y,
                x + bitmap.getWidth(),
                y + bitmap.getHeight()
        );
    }

    public void update(int moveX){
        x += moveX;

        if(x < 0){
            x = 0;
        }else if(x > maxX){
            x = maxX;
        }

        detectCollision.left = x;
        detectCollision.right = x + bitmap.getWidth();
    }

}

package bachelors.fmi.uni.sudjukninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Random;

public class LilSausage {
    Bitmap bitmap;
    int x;
    int y;
    int maxX;
    int maxY;

    Context context;

    int speed;
    Random random = new Random();

    public LilSausage(Context context, int sizeX, int sizeY){
        this.context = context;

        bitmap = BitmapFactory.
                decodeResource(context.getResources(),
                        R.drawable.sausage6);


//        int xO = 30;
//        int yO = 16;
//
//        int scale = random.nextInt(4)+1;
//
//        xO = xO/scale;
//        yO = yO/scale;
//
//        bitmap = getResizedBitmap(bitmap,
//                random.nextInt(xO),
//                random.nextInt(yO));


        maxX = sizeX - bitmap.getWidth();
        maxY = sizeY;

        y = random.nextInt(maxY);
        x = random.nextInt(maxX);
        speed = random.nextInt(10) + 1;
    }

    public void update(){
        y += speed;

        if(y > maxY){
            y = -50;
            x = random.nextInt(maxX);
            speed = random.nextInt(10);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}

package com.hanson.geoclick.Helper;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by lily on 2017-06-18.
 */

public class ImageHelper {

    public byte[] getByteArrayFromDrawable(Drawable d)
    {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    public byte[] getByteArrayFromBitmap(Bitmap d)
    {

        // convert bitmap to byte

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        d.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }

    public Bitmap getBitmapFromByteArray(byte[] b)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }


    public Bitmap resizeBitmapImage(
            Bitmap bmpSource,int maxResolution){
        int iWidth = bmpSource.getWidth();
        int iHeight = bmpSource.getHeight();
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;


        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }


}

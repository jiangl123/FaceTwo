package com.example.liu04.facetest.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by jiangling on 2018/5/18.
 */

public class BitmapUtil {

    public static Bitmap getBitmap(String path, int maxWidth, int maxHeight) {
        //先解析图片边框的大小
        Bitmap bm = null;
        File file = new File(path);
        if (file.exists()) {
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inJustDecodeBounds = true;
            ops.inSampleSize = 1;
            BitmapFactory.decodeFile(path, ops);
            int oHeight = ops.outHeight;
            int oWidth = ops.outWidth;

            //控制压缩比
            int contentHeight = maxWidth;
            int contentWidth = maxHeight;
            if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
                ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
            } else {
                ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
            }
            ops.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, ops);

        }
        return bm;
    }

    public static byte[] bitmapToByte(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}

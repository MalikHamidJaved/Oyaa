package com.verbosetech.yoohoo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

import id.zelory.compressor.Compressor;

/**
 * Created by mayank on 14/10/16.
 */

public class ImageCompressorUtil {

    public static File compressImage(Context context, String filePath) {
        return compressImage(context, new File(filePath));
    }

    public static File compressImage(Context context, File inputFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(inputFile.getPath(), options);

        int width = options.outWidth;
        int height = options.outHeight;
        float ratio;
        if (width > 1080) {
            ratio = (float) width / 1080;
            width = 1080;
            height *= ratio;
        }

        return new Compressor.Builder(context)
                .setMaxWidth(width)
                .setMaxHeight(height)
                .setQuality(80)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .build()
                .compressToFile(inputFile);
    }
}

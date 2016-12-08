package com.team7619.keepdoing.Iconfont;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dushiguang on 16/12/8.
 */
public class TypefaceManager {
    private TypefaceManager () {

    }

    public static class IconicTypeface {
        private static Map<Integer, IconicTypeface> mInstanceMap = new HashMap<>();
        private final int mTypefaceResourceId;
        private Typeface mTypeface;

        private IconicTypeface(int typefaceResourceId) {
            mTypefaceResourceId = typefaceResourceId;
        }

        public static IconicTypeface getInstance(int typefaceResourceId) {
            IconicTypeface instance = mInstanceMap.get(typefaceResourceId);
            if(instance == null) {
                instance = new IconicTypeface(typefaceResourceId);
                mInstanceMap.put(typefaceResourceId, instance);
            }
            return instance;
        }

        public Typeface getmTypeface(final Context context) {
            if (mTypeface == null) {
                mTypeface = createTypefaceFromResource(context, mTypefaceResourceId);
            }
            return mTypeface;
        }
    }

    private static Typeface createTypefaceFromResource(final Context context, final int resource) {
        Typeface typeface = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        inputStream = context.getResources().openRawResource(resource);

        String outPath = context.getCacheDir() + "/tmp.raw";

        try {
            byte[] buffer = new byte[inputStream.available()];
            outputStream = new BufferedOutputStream(new FileOutputStream(outPath));
            int l = 0;
            while ((l = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, l);
            }
            typeface = Typeface.createFromFile(outPath);
            new File(outPath).delete();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }

                if(outputStream !=null) {
                    outputStream.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return typeface;
    }
}

package com.github.lakeshire.lemon.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

/**
 * Created by liuhan on 2016/3/23.
 */
public class ImageUtil {

    private static ImageUtil sHttpUtil;
    private final Context mContext;
    private Picasso picasso;
    private int placeHolder = -1;
    public static ImageUtil getInstance(Context context) {
        if (sHttpUtil == null) {
            synchronized (ImageUtil.class) {
                if (sHttpUtil == null) {
                    sHttpUtil = new ImageUtil(context);
                }
            }
        }
        return sHttpUtil;
    }

    private ImageUtil(Context context) {
        picasso = new Picasso.Builder(context).memoryCache(new LruCache(calculateMemoryCacheSize(context))).build();
        picasso.setLoggingEnabled(false);
        mContext = context;
    }

    static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = 4;
        if (am != null) {
            memoryClass = am.getMemoryClass() / 7 > 5 ? 5 : am.getMemoryClass() / 15;
        }
        return 1024 * 1024 * memoryClass;
    }

    public void setPlaceHolder(int holder) {
        placeHolder = holder;
    }

    public void setImage(ImageView iv, String src) {
       setImage(iv, src, 256, 256, false);
    }

    public void setImage(ImageView iv, int src) {
        setImage(iv, src, 256, 256, false);
    }

    public void setImage(ImageView iv, String src, int width, int height, boolean isCircle) {
        if (src != null && !src.isEmpty()) {
            RequestCreator request = picasso.load(src);
            if (placeHolder != -1) {
                request.placeholder(placeHolder);
            }
            if (width != 0 && height != 0) {
                request.resize(width, height);
            }
            if (isCircle) {
                request.transform(new CircleTransform());
            }
            request.into(iv);
        }
    }

    public void setImageDecrease(ImageView iv, String src) {
        if (src != null && !src.isEmpty()) {
            RequestCreator request = picasso.load(src);
            if (placeHolder != -1) {
                request.placeholder(placeHolder);
            }
            request.transform(new DecreaseTransFormation());
            request.into(iv);
        }
    }

    public void setImage(ImageView iv, int src, int width, int height, boolean isCircle) {
        RequestCreator request = picasso.load(src);
        if (placeHolder != -1) {
            request.placeholder(placeHolder);
        }
        if (width != 0 && height != 0) {
            request.resize(width, height);
        }
        if (isCircle) {
            request.transform(new CircleTransform());
        }
        request.into(iv);
    }

    class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    class DecreaseTransFormation implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = ScreenUtil.getScreenWidth((Activity) mContext);

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };
}

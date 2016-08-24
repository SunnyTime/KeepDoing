package com.team7619.keepdoing.myviews.CircleImage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.team7619.keepdoing.R;

/**
 * 圆形图片显示
 * Created by ex-dushiguang201 on 2015-11-13.
 */
public class RoundImageView extends ImageView {

    private int mBorderThickness = 0;//边框的宽度
    private Context mContext;

    private int defaultColor = 0xFFFF0000;

    /**
     * 圆圈的内部颜色和外部颜色
     */
    private int mBorderOutsideColor = 0;
    private int mBorderInsideColor = 0;
    /**
     * 控件默认的长,宽
     */
    private int defaultWidth = 0;
    private int defaultHeight = 0;

    public RoundImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setCustomAttributes(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.roundedimageview);
        mBorderThickness = a.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
        mBorderOutsideColor = a.getColor(R.styleable.roundedimageview_border_outside_color,defaultColor);
        mBorderInsideColor = a.getColor(R.styleable.roundedimageview_border_inside_color, defaultColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        this.measure(0,0);

        if (drawable.getClass() == NinePatchDrawable.class) {
            return;
        }

        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }

        if (defaultHeight == 0) {
            defaultHeight =getHeight();
        }

        int radius = 0;//半径
        if (mBorderInsideColor != defaultColor && mBorderOutsideColor != defaultColor) {//外圆边框和内圆边框
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2 - 2 * mBorderThickness;
            //画内圆
            drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderInsideColor);
            //画外圆
            drawCircleBorder(canvas, radius + mBorderThickness + mBorderThickness / 2, mBorderOutsideColor);
        } else if (mBorderInsideColor != defaultColor && mBorderOutsideColor == defaultColor) {
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) /2 - mBorderThickness;
            drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderInsideColor);
        } else if (mBorderInsideColor == defaultColor && mBorderOutsideColor != defaultColor) {
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) /2 - mBorderThickness;
            drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderOutsideColor);
        } else {
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2;
        }
        Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight / 2 - radius, null);
    }

    /**
     * 获取裁剪后的原型图片
     * @param bmp
     * @param radius
     * @return
     */
    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;//比例裁剪
        // 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
        int diameter = radius * 2; //直径
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();

        int squareWidth = 0;//方形的宽
        int squareHeight = 0;//方形的长

        int x = 0;
        int y = 0;

        Bitmap squareBitmap;//方形图片

        if (bmpHeight > bmpWidth) {//高大于宽
            squareWidth = squareHeight = bmpWidth;
            x = 0;
            y = (bmpHeight - bmpWidth) / 2;

            //截取正方形图片
            /**
             * bmp：要从中截图的原始位图
             * int x:起始x坐标
             * int y：起始y坐标
             * int squareWidth：要截的图的宽度
             * int squareHeight：要截的图的宽度
             */
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
        } else if (bmpHeight < bmpWidth) {
            squareWidth = squareHeight = bmpHeight;
            x = (bmpWidth - bmpWidth) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
        } else {
            squareBitmap = bmp;
        }

        if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        } else {
            scaledSrcBmp = squareBitmap;
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());

        //抗锯齿
        paint.setAntiAlias(true);

        //如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
        //速度，本设置项依赖于dither和xfermode的设置
        paint.setFilterBitmap(true);

        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setDither(true);

        canvas.drawARGB(0, 0, 0, 0);

        //canvas.drawCircle(x, y, radius, paint)
        //x、y代表坐标、radius是半径、paint是画笔，就是画图的颜色;
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2, paint);

        //setXfermode(Xfermode xfermode);
        //设置图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        bmp = null;
        squareBitmap =null;
        scaledSrcBmp =null;
        return output;
    }


    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        //设置paint的style为Stroke,空心
        paint.setStyle(Paint.Style.STROKE);
        //边框的宽度
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
    }
}



















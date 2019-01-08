package com.fly.tour.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ScrollView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Description: <BitmapUtil><br>
 * <ul>
 * <h1>功能列表：</h1>
 * <li>1.将scrollView转化为bitmap</li>
 * <li>2.将View转化为bitmap</li>
 * <li>3.将文本和view合拼生成一个bitmap</li>
 * <li>4.将一个bitmap转化为一个字节数组</li>
 * <li>5.将一个字节数组转化为一个bitmap</li>
 * <li>6.等比压缩Bitmap到720P内</li>
 * <li>7.图片压缩到 100 * 100 像素</li>
 * <li>8.图片压缩到指定的size</li>
 * <li>9.bitmap缩放到指定的宽和高</li>
 * <li>10.网络url获取Btimep,非异步操作,会有延迟</li>
 * <li>11.读取图片的旋转的角度</li>
 * <li>12.将图片按照某个角度进行旋转</li>
 * </ul>
 * Author: gxl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class BitmapUtil {
    /**
     * 截取竖scrollview的屏幕
     *
     * 因为部分手机屏幕分辨率大,导致生成的bitmap会出现OOM异常,所以将view的bitmap设置为720p
     *
     * @param scrollView
     * @return
     */
    public static Bitmap convertViewToBitmap(ScrollView scrollView) {
        int h = 0;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.WHITE); // 透明色背景会出现黑色,设置为白色,应该考虑父级背景色
        }
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return scaled720Bitmap(bitmap);
    }

    /**
     * 将View生成bitmap
     *
     * 因为部分手机屏幕分辨率大,导致生成的bitmap会出现OOM异常,所以将view的bitmap设置为720p
     *
     * @param view 要生成bitmap的View
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        if (view == null) return null;
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(scaled720Bitmap(view.getDrawingCache()));
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 合并Bitmap,该方法主要用于微信分享,添加标题栏的合并
     *
     * @return
     */
    public static Bitmap combineBitmap_Title(Context context, String titleStr, View view) {
        if (context == null || view == null) return null;

        Bitmap bitmap = view instanceof ScrollView
                ? convertViewToBitmap((ScrollView) view)
                : convertViewToBitmap(view);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(DisplayUtil.sp2px(16));
        // 文本的宽高
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float textWidth = textPaint.measureText(titleStr);
        float textHeight = (float) Math.ceil(fm.descent - fm.ascent);

        // 标题栏的高度
        int titleHeight = DisplayUtil.dip2px(40);
        // 先生成标题栏的bitmap,因为根据当前设备制作的标题栏要进行720P的压缩
        Bitmap titleBitmap = Bitmap.createBitmap(bitmap.getWidth(), titleHeight, Bitmap.Config.RGB_565);
        Canvas titleCanvas = new Canvas(titleBitmap);
        // 绘制标题栏背景色
        Paint titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        // ******** 5.0标题颜色待确定 ********************
        // titlePaint.setColor(context.getResources().getColor(R.color.title_bg));
        titlePaint.setStrokeWidth(titleHeight);
        titleCanvas.drawLine(0, titleHeight / 2, bitmap.getWidth(), titleHeight / 2, titlePaint);
        // 绘制标题文字
        titleCanvas.drawText(titleStr, bitmap.getWidth() / 2 - textWidth / 2,
                titleHeight / 2 + textHeight / 3, textPaint);
        //??? titleCanvas.save(Canvas.ALL_SAVE_FLAG);
        titleCanvas.restore();
        titleBitmap = Bitmap.createScaledBitmap(titleBitmap, 720,
                (int) ((720f / titleBitmap.getWidth()) * titleBitmap.getHeight()), true);
        // 合成两个bitmap
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight() + titleBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(titleBitmap, 0, 0, null);
        canvas.drawBitmap(bitmap, 0, titleBitmap.getHeight(), null);
        //????canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        bitmap.recycle();
        titleBitmap.recycle();
        return newBitmap;
    }

    public static byte[] bmpToByteArray(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length == 0) return null;

        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        if (bmp == null) return null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 等比压缩Bitmap到720P内
     *
     * @param bitmap
     * @return
     */
    private static Bitmap scaled720Bitmap(Bitmap bitmap) {
        if (bitmap == null) return null;
        int width, height;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            if (bitmap.getHeight() <= 720) return bitmap;
            height = 720;
            width = (int) ((720f / bitmap.getHeight()) * bitmap.getWidth());
        } else {
            if (bitmap.getWidth() <= 720) return bitmap;
            width = 720;
            height = (int) ((720f / bitmap.getWidth()) * bitmap.getHeight());
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 图片压缩到 100 * 100 像素
     *
     * @param bitmap
     * @return
     */
    public static Bitmap scaleTo100Bitmap(Bitmap bitmap) {
        return scaleTo(bitmap, 100, 100);
    }

    public static byte[] compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        // ByteArrayInputStream isBm = new
        // ByteArrayInputStream(baos.toByteArray());//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
        // 把ByteArrayInputStream数据生成图片
        return baos.toByteArray();
    }

    public static Bitmap compressImage(Bitmap image, int targetSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        Log.d("bitmapUtil", "要压缩的bitmap大小 : " + baos.toByteArray().length / 1024);
        int options = 100;
        while (baos.toByteArray().length / 1024 > targetSize) { // 循环判断如果压缩后图片是否大于指定的kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        Log.d("bitmapUtil", "压缩后的bitmap大小 : " + baos.toByteArray().length / 1024);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
        // 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap scaleTo(Bitmap bitmap, int width, int height) {

        if (bitmap == null) return null;
        int w, h;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            if (bitmap.getHeight() <= height) return bitmap;
            h = height;
            w = (int) ((100f / bitmap.getHeight()) * bitmap.getWidth());
        } else {
            if (bitmap.getWidth() <= width) return bitmap;
            w = width;
            h = (int) ((100f / bitmap.getWidth()) * bitmap.getHeight());
        }
        return Bitmap.createScaledBitmap(bitmap, w, h, true);
    }

    /**
     * 网络url获取Btimep,非异步操作,会有延迟
     *
     * @param strUrl
     * @return
     */
    public static Bitmap getBitMap(String strUrl) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            URL url = new URL(strUrl);
            URLConnection conn = url.openConnection();
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    /**
     * 将bitmap压缩至目标大小
     *
     * @param maxImageSize
     * @param filter
     * @return
     */
    public static Bitmap scaleDown(Uri imageUri, float maxImageSize, boolean filter) {
        Bitmap result = null;
        try {
            File file = new File(new URI(imageUri.toString()));
            Bitmap realImage = BitmapFactory.decodeFile(imageUri.getPath());
            long size = file.length();
            float ratio = size / maxImageSize;
            int width = Math.round((float) realImage.getWidth() / ratio);
            int height = Math.round((float) realImage.getHeight() / ratio);

            result = Bitmap.createScaledBitmap(realImage, width, height, filter);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap decodeSampledBitmapFromResource(Uri imageUri, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageUri.getPath(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageUri.getPath(), options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;


            // final int halfHeight = height / 2;
            // final int halfWidth = width / 2;
            //
            // while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
            // {
            // inSampleSize *= 2;
            // }
        }

        return inSampleSize;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm 需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {}
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 获取压缩后的图片
     *
     * @return
     */
    public static Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
    public static int[] getImageSize(String url) {
        int[] size = new int[]{0, 0};
        if (FileUtil.isImageFile(url)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 设置为true,表示解析Bitmap对象，该对象不占内存
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(url, options);
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            // 图片宽高
            switch (getBitmapDegree(url)) {
                case 90:
                case 270:
                    return new int[]{options.outHeight, options.outWidth};
                default:
                    return new int[]{options.outWidth, options.outHeight};
            }
        }
        return size;
    }
}
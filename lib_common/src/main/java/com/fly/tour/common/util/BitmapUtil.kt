package com.fly.tour.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec
import android.widget.ScrollView

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.net.URLConnection

/**
 * Description: <BitmapUtil><br>
 *
 * <h1>功能列表：</h1>
 *  * 1.将scrollView转化为bitmap
 *  * 2.将View转化为bitmap
 *  * 3.将文本和view合拼生成一个bitmap
 *  * 4.将一个bitmap转化为一个字节数组
 *  * 5.将一个字节数组转化为一个bitmap
 *  * 6.等比压缩Bitmap到720P内
 *  * 7.图片压缩到 100 * 100 像素
 *  * 8.图片压缩到指定的size
 *  * 9.bitmap缩放到指定的宽和高
 *  * 10.网络url获取Btimep,非异步操作,会有延迟
 *  * 11.读取图片的旋转的角度
 *  * 12.将图片按照某个角度进行旋转
 *
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</BitmapUtil> */
object BitmapUtil {
    /**
     * 截取竖scrollview的屏幕
     *
     * 因为部分手机屏幕分辨率大,导致生成的bitmap会出现OOM异常,所以将view的bitmap设置为720p
     *
     * @param scrollView
     * @return
     */
    fun convertViewToBitmap(scrollView: ScrollView): Bitmap? {
        var h = 0
        // 获取scrollview实际高度
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i).setBackgroundColor(Color.WHITE) // 透明色背景会出现黑色,设置为白色,应该考虑父级背景色
        }
        val bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return scaled720Bitmap(bitmap)
    }

    /**
     * 将View生成bitmap
     *
     * 因为部分手机屏幕分辨率大,导致生成的bitmap会出现OOM异常,所以将view的bitmap设置为720p
     *
     * @param view 要生成bitmap的View
     * @return
     */
    fun convertViewToBitmap(view: View?): Bitmap? {
        if (view == null) return null
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(scaled720Bitmap(view.drawingCache)!!)
        view.destroyDrawingCache()
        return bitmap
    }

    /**
     * 合并Bitmap,该方法主要用于微信分享,添加标题栏的合并
     *
     * @return
     */
    fun combineBitmap_Title(context: Context?, titleStr: String, view: View?): Bitmap? {
        if (context == null || view == null) return null

        val bitmap = if (view is ScrollView) convertViewToBitmap((view as ScrollView?)!!) else convertViewToBitmap(view)

        val textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE
        textPaint.textSize = DisplayUtil.sp2px(16f).toFloat()
        // 文本的宽高
        val fm = textPaint.fontMetrics
        val textWidth = textPaint.measureText(titleStr)
        val textHeight = Math.ceil((fm.descent - fm.ascent).toDouble()).toFloat()

        // 标题栏的高度
        val titleHeight = DisplayUtil.dip2px(40f)
        // 先生成标题栏的bitmap,因为根据当前设备制作的标题栏要进行720P的压缩
        var titleBitmap = Bitmap.createBitmap(bitmap!!.width, titleHeight, Bitmap.Config.RGB_565)
        val titleCanvas = Canvas(titleBitmap)
        // 绘制标题栏背景色
        val titlePaint = Paint()
        titlePaint.isAntiAlias = true
        // ******** 5.0标题颜色待确定 ********************
        // titlePaint.setColor(context.getResources().getColor(R.color.title_bg));
        titlePaint.strokeWidth = titleHeight.toFloat()
        titleCanvas.drawLine(0f, (titleHeight / 2).toFloat(), bitmap.width.toFloat(), (titleHeight / 2).toFloat(), titlePaint)
        // 绘制标题文字
        titleCanvas.drawText(titleStr, bitmap.width / 2 - textWidth / 2, titleHeight / 2 + textHeight / 3, textPaint)
        //??? titleCanvas.save(Canvas.ALL_SAVE_FLAG);
        titleCanvas.restore()
        titleBitmap = Bitmap.createScaledBitmap(titleBitmap, 720, (720f / titleBitmap.width * titleBitmap.height).toInt(), true)
        // 合成两个bitmap
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height + titleBitmap.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(newBitmap)
        canvas.drawBitmap(titleBitmap, 0f, 0f, null)
        canvas.drawBitmap(bitmap, 0f, titleBitmap.height.toFloat(), null)
        //????canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore()
        bitmap.recycle()
        titleBitmap.recycle()
        return newBitmap
    }

    fun bmpToByteArray(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    fun bytesToBimap(b: ByteArray): Bitmap? {
        return if (b.size == 0) null else BitmapFactory.decodeByteArray(b, 0, b.size)

    }

    fun bmpToByteArray(bmp: Bitmap?, needRecycle: Boolean): ByteArray? {

        if (bmp == null) return null
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 等比压缩Bitmap到720P内
     *
     * @param bitmap
     * @return
     */
    private fun scaled720Bitmap(bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) return null
        val width: Int
        val height: Int
        if (bitmap.width > bitmap.height) {
            if (bitmap.height <= 720) return bitmap
            height = 720
            width = (720f / bitmap.height * bitmap.width).toInt()
        } else {
            if (bitmap.width <= 720) return bitmap
            width = 720
            height = (720f / bitmap.width * bitmap.height).toInt()
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    /**
     * 图片压缩到 100 * 100 像素
     *
     * @param bitmap
     * @return
     */
    fun scaleTo100Bitmap(bitmap: Bitmap): Bitmap? {
        return scaleTo(bitmap, 100, 100)
    }

    fun compressImage(image: Bitmap): ByteArray {

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset()// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10// 每次都减少10
        }
        // ByteArrayInputStream isBm = new
        // ByteArrayInputStream(baos.toByteArray());//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
        // 把ByteArrayInputStream数据生成图片
        return baos.toByteArray()
    }

    fun compressImage(image: Bitmap, targetSize: Int): Bitmap? {

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        Log.d("bitmapUtil", "要压缩的bitmap大小 : " + baos.toByteArray().size / 1024)
        var options = 100
        while (baos.toByteArray().size / 1024 > targetSize) { // 循环判断如果压缩后图片是否大于指定的kb,大于继续压缩
            baos.reset()// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10// 每次都减少10
        }
        Log.d("bitmapUtil", "压缩后的bitmap大小 : " + baos.toByteArray().size / 1024)
        val isBm = ByteArrayInputStream(baos.toByteArray())//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    fun scaleTo(bitmap: Bitmap?, width: Int, height: Int): Bitmap? {

        if (bitmap == null) return null
        val w: Int
        val h: Int
        if (bitmap.width > bitmap.height) {
            if (bitmap.height <= height) return bitmap
            h = height
            w = (100f / bitmap.height * bitmap.width).toInt()
        } else {
            if (bitmap.width <= width) return bitmap
            w = width
            h = (100f / bitmap.width * bitmap.height).toInt()
        }
        return Bitmap.createScaledBitmap(bitmap, w, h, true)
    }

    /**
     * 网络url获取Btimep,非异步操作,会有延迟
     *
     * @param strUrl
     * @return
     */
    fun getBitMap(strUrl: String): Bitmap? {
        var bitmap: Bitmap? = null
        var `is`: InputStream? = null
        try {
            val url = URL(strUrl)
            val conn = url.openConnection()
            `is` = conn.getInputStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        bitmap = BitmapFactory.decodeStream(`is`)
        return bitmap
    }

    /**
     * 将bitmap压缩至目标大小
     *
     * @param maxImageSize
     * @param filter
     * @return
     */
    fun scaleDown(imageUri: Uri, maxImageSize: Float, filter: Boolean): Bitmap? {
        var result: Bitmap? = null
        try {
            val file = File(URI(imageUri.toString()))
            val realImage = BitmapFactory.decodeFile(imageUri.path)
            val size = file.length()
            val ratio = size / maxImageSize
            val width = Math.round(realImage.width.toFloat() / ratio)
            val height = Math.round(realImage.height.toFloat() / ratio)

            result = Bitmap.createScaledBitmap(realImage, width, height, filter)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        return result
    }

    fun decodeSampledBitmapFromResource(imageUri: Uri, reqWidth: Int, reqHeight: Int): Bitmap {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageUri.path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(imageUri.path, options)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = if (heightRatio < widthRatio) widthRatio else heightRatio


            // final int halfHeight = height / 2;
            // final int halfWidth = width / 2;
            //
            // while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
            // {
            // inSampleSize *= 2;
            // }
        }

        return inSampleSize
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    fun getBitmapDegree(path: String): Int {
        var degree = 0
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            val exifInterface = ExifInterface(path)
            // 获取图片的旋转信息
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm 需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    fun rotateBitmapByDegree(bm: Bitmap, degree: Int): Bitmap {
        var returnBm: Bitmap? = null

        // 根据旋转角度，生成旋转矩阵
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
        } catch (e: OutOfMemoryError) {
        }

        if (returnBm == null) {
            returnBm = bm
        }
        if (bm != returnBm) {
            bm.recycle()
        }
        return returnBm
    }

    /**
     * 获取压缩后的图片
     *
     * @return
     */
    fun decodeBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    /**
     * 获取图片原始的宽、高
     * @param url
     * @return
     */
    fun getImageSize(url: String): IntArray {
        val size = intArrayOf(0, 0)
        if (FileUtil.isImageFile(url)) {
            val options = BitmapFactory.Options()
            // 设置为true,表示解析Bitmap对象，该对象不占内存
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(url, options)
            options.inPreferredConfig = Bitmap.Config.RGB_565
            // 图片宽高
            when (getBitmapDegree(url)) {
                90, 270 -> return intArrayOf(options.outHeight, options.outWidth)
                else -> return intArrayOf(options.outWidth, options.outHeight)
            }
        }
        return size
    }

    fun getAdjustImageSize(context: Context, sourceWidth: Int, sourceHeight: Int): IntArray? {
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val screenHeight = ScreenUtils.getScreenHeight(context)
        //默认的宽和高
        var coverWidth = 0// - DisplayUtil.dip2px(16) * 2;
        var coverHeight = 0//

        if (sourceWidth > 0 && sourceHeight > 0) {
            //如果是横屏图
            if (sourceWidth > sourceHeight) {
                //图比屏幕还宽则以屏幕的宽为基准进行缩放
                if (sourceWidth > screenWidth) {
                    coverWidth = screenWidth//屏幕的宽就是图片的宽
                    val scaleRate = screenWidth.toFloat() / sourceWidth//缩放比例
                    coverHeight = (scaleRate * sourceHeight).toInt()//图片的宽高等比例的缩小
                } else {
                    //否则图片是多大的就显示多大的
                    coverWidth = sourceWidth
                    coverHeight = sourceHeight
                }
            } else {
                //如果是竖屏的图，且比屏幕还高则以屏幕的高为基准进行等比例缩放
                if (sourceHeight > screenHeight) {
                    coverHeight = screenHeight//屏幕的高就是图片的高
                    val scaleRate = screenHeight.toFloat() / sourceHeight//缩放比例
                    coverWidth = (scaleRate * sourceWidth).toInt()//图片的宽等比例的缩小
                } else {
                    //否则图片是多大的就显示多大的
                    coverWidth = sourceWidth
                    coverHeight = sourceHeight
                }
            }
        }
        return if (coverWidth == 0 && coverHeight == 0) {
            null
        } else intArrayOf(coverWidth, coverHeight)
    }

    fun getBitmapSize(bitmap: Bitmap): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.allocationByteCount
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            bitmap.byteCount
        } else bitmap.rowBytes * bitmap.height
        // 在低版本中用一行的字节x高度
        //earlier version
    }
}

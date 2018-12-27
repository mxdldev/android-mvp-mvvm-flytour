package com.fly.tour.common.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.fly.tour.common.R;

import java.util.ArrayList;
import java.util.WeakHashMap;


/**
 *
 * https://github.com/m5/MagicTextView
 * Description: <描边效果的TextView><br>
 * Author: gxl<br>
 * Date: 2018/8/24<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
@SuppressLint("AppCompatCustomView")
public class MagicTextView extends TextView {
  private ArrayList<Shadow> outerShadows;
  private ArrayList<Shadow> innerShadows;

  private WeakHashMap<String, Pair<Canvas, Bitmap>> canvasStore;

  private Canvas tempCanvas;
  private Bitmap tempBitmap;

  private Drawable foregroundDrawable;

  private float strokeWidth;
  private Integer strokeColor;
  private Join strokeJoin;
  private float strokeMiter;

  private int[] lockedCompoundPadding;
  private boolean frozen = false;

  public MagicTextView(Context context) {
    super(context);
    init(null);
  }

  public MagicTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public MagicTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs);
  }

  public void init(AttributeSet attrs) {
    outerShadows = new ArrayList<Shadow>();
    innerShadows = new ArrayList<Shadow>();
    if (canvasStore == null) {
      canvasStore = new WeakHashMap<String, Pair<Canvas, Bitmap>>();
    }

    if (attrs != null) {
      TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MagicTextView);

      String typefaceName = a.getString(R.styleable.MagicTextView_typeface);
      if (typefaceName != null) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
            String.format("fonts/%s.ttf", typefaceName));
        setTypeface(tf);
      }

      if (a.hasValue(R.styleable.MagicTextView_foreground)) {
        Drawable foreground = a.getDrawable(R.styleable.MagicTextView_foreground);
        if (foreground != null) {
          this.setForegroundDrawable(foreground);
        } else {
          this.setTextColor(a.getColor(R.styleable.MagicTextView_foreground, 0xff000000));
        }
      }

      if (a.hasValue(R.styleable.MagicTextView_backgroundcolor)) {
        Drawable background = a.getDrawable(R.styleable.MagicTextView_backgroundcolor);
        if (background != null) {
          this.setBackgroundDrawable(background);
        } else {
          this.setBackgroundColor(
              a.getColor(R.styleable.MagicTextView_backgroundcolor, 0xff000000));
        }
      }

      if (a.hasValue(R.styleable.MagicTextView_innerShadowColor)) {
        this.addInnerShadow(a.getDimensionPixelSize(R.styleable.MagicTextView_innerShadowRadius, 0),
            a.getDimensionPixelOffset(R.styleable.MagicTextView_innerShadowDx, 0),
            a.getDimensionPixelOffset(R.styleable.MagicTextView_innerShadowDy, 0),
            a.getColor(R.styleable.MagicTextView_innerShadowColor, 0xff000000));
      }

      if (a.hasValue(R.styleable.MagicTextView_outerShadowColor)) {
        this.addOuterShadow(a.getDimensionPixelSize(R.styleable.MagicTextView_outerShadowRadius, 0),
            a.getDimensionPixelOffset(R.styleable.MagicTextView_outerShadowDx, 0),
            a.getDimensionPixelOffset(R.styleable.MagicTextView_outerShadowDy, 0),
            a.getColor(R.styleable.MagicTextView_outerShadowColor, 0xff000000));
      }

      if (a.hasValue(R.styleable.MagicTextView_strokeColor)) {
        float strokeWidth = a.getDimensionPixelSize(R.styleable.MagicTextView_strokeWidth, 1);
        int strokeColor = a.getColor(R.styleable.MagicTextView_strokeColor, 0xff000000);
        float strokeMiter = a.getDimensionPixelSize(R.styleable.MagicTextView_strokeMiter, 10);
        Join strokeJoin = null;
        switch (a.getInt(R.styleable.MagicTextView_strokeJoinStyle, 0)) {
          case (0):
            strokeJoin = Join.MITER;
            break;
          case (1):
            strokeJoin = Join.BEVEL;
            break;
          case (2):
            strokeJoin = Join.ROUND;
            break;
        }
        this.setStroke(strokeWidth, strokeColor, strokeJoin, strokeMiter);
      }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        && (innerShadows.size() > 0 || foregroundDrawable != null)) {
      setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
  }

  public void setStroke(float width, int color, Join join, float miter) {
    strokeWidth = width;
    strokeColor = color;
    strokeJoin = join;
    strokeMiter = miter;
  }

  public void setStroke(float width, int color) {
    setStroke(width, color, Join.MITER, 10);
  }

  public void addOuterShadow(float r, float dx, float dy, int color) {
    if (r == 0) {
      r = 0.0001f;
    }
    outerShadows.add(new Shadow(r, dx, dy, color));
  }

  public void addInnerShadow(float r, float dx, float dy, int color) {
    if (r == 0) {
      r = 0.0001f;
    }
    innerShadows.add(new Shadow(r, dx, dy, color));
  }

  public void clearInnerShadows() {
    innerShadows.clear();
  }

  public void clearOuterShadows() {
    outerShadows.clear();
  }

  public void setForegroundDrawable(Drawable d) {
    this.foregroundDrawable = d;
  }

  public Drawable getForeground() {
    return this.foregroundDrawable == null
        ? this.foregroundDrawable
        : new ColorDrawable(this.getCurrentTextColor());
  }


  @Override
  public void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    freeze();
    Drawable restoreBackground = this.getBackground();
    Drawable[] restoreDrawables = this.getCompoundDrawables();
    int restoreColor = this.getCurrentTextColor();

    this.setCompoundDrawables(null, null, null, null);

    for (Shadow shadow : outerShadows) {
      this.setShadowLayer(shadow.r, shadow.dx, shadow.dy, shadow.color);
      super.onDraw(canvas);
    }
    this.setShadowLayer(0, 0, 0, 0);
    this.setTextColor(restoreColor);

    if (this.foregroundDrawable != null && this.foregroundDrawable instanceof BitmapDrawable) {
      generateTempCanvas();
      super.onDraw(tempCanvas);
      Paint paint = ((BitmapDrawable) this.foregroundDrawable).getPaint();
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
      this.foregroundDrawable.setBounds(canvas.getClipBounds());
      this.foregroundDrawable.draw(tempCanvas);
      canvas.drawBitmap(tempBitmap, 0, 0, null);
      tempCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    if (strokeColor != null) {
      TextPaint paint = this.getPaint();
      paint.setStyle(Style.STROKE);
      paint.setStrokeJoin(strokeJoin);
      paint.setStrokeMiter(strokeMiter);
      this.setTextColor(strokeColor);
      paint.setStrokeWidth(strokeWidth);
      super.onDraw(canvas);
      paint.setStyle(Style.FILL);
      this.setTextColor(restoreColor);
    }
    if (innerShadows.size() > 0) {
      generateTempCanvas();
      TextPaint paint = this.getPaint();
      for (Shadow shadow : innerShadows) {
        this.setTextColor(shadow.color);
        super.onDraw(tempCanvas);
        this.setTextColor(0xFF000000);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setMaskFilter(new BlurMaskFilter(shadow.r, BlurMaskFilter.Blur.NORMAL));

        tempCanvas.save();
        tempCanvas.translate(shadow.dx, shadow.dy);
        super.onDraw(tempCanvas);
        tempCanvas.restore();
        canvas.drawBitmap(tempBitmap, 0, 0, null);
        tempCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        paint.setXfermode(null);
        paint.setMaskFilter(null);
        this.setTextColor(restoreColor);
        this.setShadowLayer(0, 0, 0, 0);
      }
    }


    if (restoreDrawables != null) {
      this.setCompoundDrawablesWithIntrinsicBounds(restoreDrawables[0], restoreDrawables[1],
          restoreDrawables[2], restoreDrawables[3]);
    }
    this.setBackgroundDrawable(restoreBackground);
    this.setTextColor(restoreColor);

    unfreeze();
  }

  private void generateTempCanvas() {
    String key = String.format("%dx%d", getWidth(), getHeight());
    Pair<Canvas, Bitmap> stored = canvasStore.get(key);
    if (stored != null) {
      tempCanvas = stored.first;
      tempBitmap = stored.second;
    } else {
      tempCanvas = new Canvas();
      tempBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
      tempCanvas.setBitmap(tempBitmap);
      canvasStore.put(key, new Pair<Canvas, Bitmap>(tempCanvas, tempBitmap));
    }
  }


  // Keep these things locked while onDraw in processing
  public void freeze() {
    lockedCompoundPadding = new int[] {getCompoundPaddingLeft(), getCompoundPaddingRight(),
        getCompoundPaddingTop(), getCompoundPaddingBottom()};
    frozen = true;
  }

  public void unfreeze() {
    frozen = false;
  }


  @Override
  public void requestLayout() {
    if (!frozen) super.requestLayout();
  }

  @Override
  public void postInvalidate() {
    if (!frozen) super.postInvalidate();
  }

  @Override
  public void postInvalidate(int left, int top, int right, int bottom) {
    if (!frozen) super.postInvalidate(left, top, right, bottom);
  }

  @Override
  public void invalidate() {
    if (!frozen) super.invalidate();
  }

  @Override
  public void invalidate(Rect rect) {
    if (!frozen) super.invalidate(rect);
  }

  @Override
  public void invalidate(int l, int t, int r, int b) {
    if (!frozen) super.invalidate(l, t, r, b);
  }

  @Override
  public int getCompoundPaddingLeft() {
    return !frozen ? super.getCompoundPaddingLeft() : lockedCompoundPadding[0];
  }

  @Override
  public int getCompoundPaddingRight() {
    return !frozen ? super.getCompoundPaddingRight() : lockedCompoundPadding[1];
  }

  @Override
  public int getCompoundPaddingTop() {
    return !frozen ? super.getCompoundPaddingTop() : lockedCompoundPadding[2];
  }

  @Override
  public int getCompoundPaddingBottom() {
    return !frozen ? super.getCompoundPaddingBottom() : lockedCompoundPadding[3];
  }

  public static class Shadow {
    float r;
    float dx;
    float dy;
    int color;

    public Shadow(float r, float dx, float dy, int color) {
      this.r = r;
      this.dx = dx;
      this.dy = dy;
      this.color = color;
    }
  }
}

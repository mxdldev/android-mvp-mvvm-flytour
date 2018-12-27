package com.fly.tour.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.fly.tour.common.R;


/**
 * Description: <公共的activity基类><br>
 * Author: gxl<br>
 * Date: 2018/6/6<br>
 * Version: V1.0.0<br>
 * Update: <br>
 * 
 */
public abstract class BaseActivity extends AppCompatActivity {
  public static final String TAG = "YESWAY";
  public BaseActivity mActivity;
  private TextView mTxtTitle;
  private Toolbar mToolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityCollectManager.addActivity(this);
    mActivity = this;
    initView(savedInstanceState);
    initData();
  }

  @Override
  public void setContentView(int layoutResID) {
    //加载跟布局
    super.setContentView(R.layout.base_activity);
    mToolbar = findView(R.id.toolbar);
    setSupportActionBar(mToolbar);
    if (mTxtTitle != null && getSupportActionBar() != null){
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    // 加载子类要显示的布局
    ViewStub content_viewStub = (ViewStub) findViewById(R.id.content_viewStub);
    content_viewStub.setLayoutResource(layoutResID);
    content_viewStub.inflate();

    mTxtTitle = findView(R.id.txt_toolbar_title);
  }

  @Override
  protected void onDestroy() {
    ActivityCollectManager.removeActivity(this);
    super.onDestroy();
  }



  public abstract void initView(Bundle savedInstanceState);

  public abstract void initData();

  public <M extends View> M findView(int id) {
    return (M) findViewById(id);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (null != this.getCurrentFocus()) {

      InputMethodManager mInputMethodManager =
          (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
          0);
    }
    return super.onTouchEvent(event);
  }

  public void closeAllActivity() {
    ActivityCollectManager.finishAllActivity();
  }

  public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
    Intent intent = new Intent(this, targetActivityClass);
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    startActivity(intent);
  }
  public void showLoading() {

  }

  public void hideLoading() {

  }

  public void showErrNetWork() {

  }

  @Override
  protected void onTitleChanged(CharSequence title, int color) {
    super.onTitleChanged(title, color);
    if(mTxtTitle != null){
      mTxtTitle.setText(title);
    }
  }
}

package com.fly.tour.common.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fly.tour.common.R;
import com.fly.tour.common.util.DisplayUtil;
import com.fly.tour.common.util.log.KLog;

public class CommonDialogFragment extends DialogFragment {
    public static final String TAG = CommonDialogFragment.class.getSimpleName();
    private OnDialogClickListener mOnDialogClickListener;
    private static boolean isShowing = false;//避免弹多个dialog
    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
        KLog.v(TAG,"dismiss start...");
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        isShowing = true;
    }

    public static boolean isShowing() {
        return isShowing;
    }
    public CommonDialogFragment setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        mOnDialogClickListener = onDialogClickListener;
        return this;
    }

    public interface OnDialogClickListener {
        void onLeftBtnClick(View view);
        void onRightBtnClick(View view);
    };
    public static CommonDialogFragment newInstance(Builder builder){
        CommonDialogFragment commonDialogFragment = new CommonDialogFragment();
        Bundle args = new Bundle();
        args.putString("title",builder.title);
        args.putString("describe",builder.describe);
        args.putString("leftbtn",builder.leftbtn);
        args.putString("rightbtn",builder.rightbtn);
        args.putInt("rightbtncolor", builder.btnRightTextColor);
        commonDialogFragment.mOnDialogClickListener = builder.mListener;
        commonDialogFragment.setArguments(args);
        return commonDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(getResources().getDisplayMetrics().widthPixels - DisplayUtil.dip2px(40) * 2, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_dialog, container, false);
        Bundle arguments = getArguments();
        String title = null;
        String describe = null;
        String leftbtn = null;
        String rightbtn = null;
        int rightBtnTextColor = 0;
        if (arguments != null) {
            title = arguments.getString("title");
            describe = arguments.getString("describe");
            leftbtn = arguments.getString("leftbtn");
            rightbtn = arguments.getString("rightbtn");
            rightBtnTextColor = arguments.getInt("rightbtncolor", 0);
        }
        TextView txtTitle = view.findViewById(R.id.txt_common_dialog_title);
        TextView txtDescribe = view.findViewById(R.id.txt_common_dialog_describe);
        Button btnLeft = view.findViewById(R.id.btn_common_dialog_left);
        Button btnRight = view.findViewById(R.id.btn_common_dialog_right);
        View btnHalving = view.findViewById(R.id.view_halving_line);
        if (!TextUtils.isEmpty(title)) {
            txtTitle.setText(title);
        }else{
            txtTitle.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(describe)){
            txtDescribe.setText(describe);
        }
        if (!TextUtils.isEmpty(leftbtn)) {
            btnHalving.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(leftbtn);
        } else {
            btnHalving.setVisibility(View.GONE);
            btnLeft.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(rightbtn)) {
            btnRight.setText(rightbtn);
        }
        if (rightBtnTextColor != 0) {
            btnRight.setTextColor(rightBtnTextColor);
        }
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnDialogClickListener != null){
                    mOnDialogClickListener.onLeftBtnClick(view);
                }
                dismiss();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mOnDialogClickListener != null){
                    mOnDialogClickListener.onRightBtnClick(view);
                }
                dismiss();
            }
        });
        return view;
    }
    public static class  Builder{
        String title;
        String describe;
        String leftbtn;
        String rightbtn;
        int btnRightTextColor;
        OnDialogClickListener mListener;

        public Builder() {
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        public Builder setDescribe(String describe) {
            this.describe = describe;
            return this;
        }
        public Builder setLeftbtn(String leftbtn) {
            this.leftbtn = leftbtn;
            return this;
        }
        public Builder setRightbtn(String rightbtn) {
            this.rightbtn = rightbtn;
            return this;
        }
        public Builder setOnDialogClickListener(OnDialogClickListener listener) {
            this.mListener = listener;
            return this;
        }

        public Builder setRightbtnTextColor(int rightBtnTextcolor) {
            this.btnRightTextColor = rightBtnTextcolor;
            return this;
        }

        public CommonDialogFragment build() {
            return CommonDialogFragment.newInstance(this);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        isShowing = false;
        KLog.v(TAG,"onCancel start...");
    }
}

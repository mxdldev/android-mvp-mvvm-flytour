package com.fly.tour.me.mvvm;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fly.tour.common.mvvm.binding.command.BindingAction;
import com.fly.tour.common.mvvm.binding.command.BindingCommand;
import com.fly.tour.common.mvvm.viewmodel.BaseViewModel;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.mvvm.model.NewsTypeAddModel;

public class NewsTypeAddViewModel extends BaseViewModel<NewsTypeAddModel> {
    public ObservableField<String> typeName = new ObservableField<>("");
    public NewsTypeAddViewModel(@NonNull Application application, NewsTypeAddModel model) {
        super(application, model);
    }
    //登录按钮的点击事件
    public BindingCommand addOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(TextUtils.isEmpty(typeName.get())){
                 ToastUtil.showToast("请输入新闻类型");
                 return;
            }
        }
    });
    public void onClickBtnAddNewsType(){
        if(TextUtils.isEmpty(typeName.get())){
            ToastUtil.showToast("请输入新闻类型");
            return;
        }
    }
}
